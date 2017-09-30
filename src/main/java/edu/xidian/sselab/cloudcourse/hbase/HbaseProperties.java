package edu.xidian.sselab.cloudcourse.hbase;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class HbaseProperties {
    
    @Value("${hbase.nodes}")
    private String hbaseNodes;
    
    // solve @Value cannot resolve ${}
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
