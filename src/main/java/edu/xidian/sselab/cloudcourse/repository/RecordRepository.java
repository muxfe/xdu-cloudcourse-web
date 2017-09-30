package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecordRepository {

    private static final String TABLE_NAME = "Record";

    private static final String FAMILY_NAME = "info";
    
    private final HbaseClient hbaseClient;

    @Autowired
    public RecordRepository(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    public List<Record> findAllByRecord(Record record){
        List<Record> resultRecords = new ArrayList<>();
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        if (record.getPlaceId() != null) {
            RowFilter rowFilter = new RowFilter(
                CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator(record.getPlaceId() + "##"));
            filterList.addFilter(rowFilter);
        }
        if (record.getTime() != null) {
            RowFilter rowFilter = new RowFilter(
                CompareFilter.CompareOp.EQUAL,
                new SubstringComparator("##" + record.getTime() + "##"));
            filterList.addFilter(rowFilter);
        }
        if (StringUtils.isNotEmpty(record.getEid())) {
            RowFilter rowFilter = new RowFilter(
                CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator("##" + record.getEid() + "$"));
            filterList.addFilter(rowFilter);
        }
        
        Scan scan = new Scan();
        if (filterList.getFilters().size() != 0) {
            scan.setFilter(filterList);
        }
        ResultScanner rs;
        try {
            if (table != null) {
                rs = table.getScanner(scan);
                for (Result result : rs) {
                    Record tempRecord = new Record().mapFrom(result);
                    resultRecords.add(tempRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("查询出错，返回一个空的列表!");
        }
        return resultRecords;
    }

    public boolean save(List<Record> allRecords){
        List<Put> allPuts = new ArrayList<>();
        for (Record record : allRecords) {
            // 1.确定行键 （placeID##time##eid）
            String rowKey = record.getPlaceId() + "##" + record.getTime() + "##" + record.getEid();
            Put put = new Put(Bytes.toBytes(rowKey));
            // 2.添加列
            put.addColumn(
                Bytes.toBytes(FAMILY_NAME),
                Bytes.toBytes("address"),
                Bytes.toBytes(record.getAddress()));

            put.addColumn(
                Bytes.toBytes(FAMILY_NAME),
                Bytes.toBytes("longitude"),
                Bytes.toBytes(String.valueOf(record.getLongitude())));

            put.addColumn(
                Bytes.toBytes(FAMILY_NAME),
                Bytes.toBytes("latitude"),
                Bytes.toBytes(String.valueOf(record.getLatitude())));

            allPuts.add(put);
        }

        // 3.执行数据库插入操作
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        try {
            if (table != null) {
                table.put(allPuts);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
