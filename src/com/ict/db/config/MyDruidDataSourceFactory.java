package com.ict.db.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 整合druid连接池
 *
 * @Author: Lizbeth9421
 * @Date: 2022/04/16/23:15
 */
public class MyDruidDataSourceFactory extends PooledDataSourceFactory {
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void setProperties(final Properties properties) {
        try {
            this.dataSource= DruidDataSourceFactory.createDataSource(properties);
        } catch (final RuntimeException e) {
            throw  e;
        }catch (final Exception e) {
            throw new RuntimeException("初始化连接错误",e);
        }
    }


}
