package com.example.demo.conf;

import lombok.Data;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.security.DenyAll;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 侯玢
 * @Description: 实现数据源读写分离
 * @Date: Created in 23:23 2018/3/22
 */
@Data
public class DyamicDataSource extends AbstractRoutingDataSource{
    private Object readDataSource;
    private Object writeDataSource;

    @Override
    public void afterPropertiesSet() {
        if (null == this.writeDataSource){
            throw new IllegalArgumentException("动态数据源异常！ DyamicDataSource");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DyamicDataSourceType.WRITE.name(),writeDataSource);
        if (readDataSource != null){
            targetDataSource.put(DyamicDataSourceType.READ.name(),readDataSource);
        }
        setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    /**
      *@Description: 数据源标志，让Spring切换到指定数据源
      *
      */

    @Override
    protected Object determineCurrentLookupKey() {
        DyamicDataSourceType D = DyamicDataSourceHolder.getDataSource();
        if (D == null ||D == DyamicDataSourceType.WRITE){return DyamicDataSourceType.WRITE.name();}
        return DyamicDataSourceType.READ.name();
    }

}
