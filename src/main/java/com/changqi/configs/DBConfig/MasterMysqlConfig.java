package com.changqi.configs.DBConfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

// 扫描 Mapper 接口并容器管理 (针对该datasource的处理接口),
// @MapperScan 扫描 Mapper 接口并容器管理，包路径精确到 master 目录，为了和 slave 数据源做到精确区分
// 参考：https://www.bysocket.com/?p=1712
//@MapperScan(basePackages = MasterMysqlConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
//@Configuration
public class MasterMysqlConfig {
    // 精确到 master 目录，以便跟其他数据源隔离
    //static final String PACKAGE = "org.spring.springboot.dao.master";
    //如果有通过xml配置的话，通常我们都会通过 Mapper 接口实现（即将 SQL 与 java 代码放在一起）
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Value("${spring.datasource.master.url}")
    private String url;

    @Value("${spring.datasource.master.username}")
    private String user;

    @Value("${spring.datasource.master.password}")
    private String password;

    @Value("${spring.datasource.master.driverClassName}")
    private String driverClass;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
/*
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterMysqlConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
    */
}
