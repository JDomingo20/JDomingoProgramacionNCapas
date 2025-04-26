package com.JDomingoProgramacionNCapas.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    @Bean // Para gestionar configuraciones personalizadas
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
        dataSource.setUsername("JDomingoProgramacionNCapas");
        dataSource.setPassword("password1");
        
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource  dataSource){
        return new JdbcTemplate(dataSource);
    }
}
