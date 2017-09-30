package edu.xidian.sselab.cloudcourse.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HbaseClient {

    private Configuration configuration;

    private Connection connection;
    
    
    @Autowired
    public HbaseClient(HbaseProperties hbaseProperties) {
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", hbaseProperties.getHbaseNodes());
    }

    public Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                System.out.println("HBase数据库连接关闭，尝试重新连接失败!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public Table getTableByName(String tableName) {
        try {
            return connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            System.out.println("连接数据库表失败!");
            e.printStackTrace();
        }
        return null;
    }
    
}
