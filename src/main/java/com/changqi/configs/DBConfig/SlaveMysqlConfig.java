package com.changqi.configs.DBConfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@MapperScan(basePackages = SlaveMysqlConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
//@Configuration
public class SlaveMysqlConfig {
    //static final String PACKAGE = "org.spring.springboot.dao.slave";
    static final String MAPPER_LOCATION = "classpath:slave/slave/*.xml";

    @Value("${spring.datasource.slave.url}")
    private String url;

    @Value("${spring.datasource.slave.username}")
    private String user;

    @Value("${spring.datasource.slave.password}")
    private String password;

    @Value("${spring.datasource.slave.driverClassName}")
    private String driverClass;

    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    /*
    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("slaveDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SlaveMysqlConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
    */
}
