package org.mesutormanli.customerapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersistenceConfigTest {

    private Environment env;
    private DataSourceConfig dataSourceConfig;
    private JpaConfig jpaConfig;

    @BeforeEach
    void setUp() {
        env = mock(Environment.class);
        dataSourceConfig = new DataSourceConfig(env);
        jpaConfig = new JpaConfig(env);
    }

    @Test
    void testDataSource() {
        when(env.getProperty("dataSource.driverClassName")).thenReturn("org.sqlite.JDBC");
        when(env.getProperty("dataSource.url")).thenReturn("jdbc:sqlite:CustomerDB.sqlite");

        DataSource dataSource = dataSourceConfig.dataSource();

        assertNotNull(dataSource);
        assertTrue(dataSource instanceof DriverManagerDataSource);
        DriverManagerDataSource dmDataSource = (DriverManagerDataSource) dataSource;
        assertEquals("jdbc:sqlite:CustomerDB.sqlite", dmDataSource.getUrl());
    }

    @Test
    void testEntityManagerFactory() {
        when(env.getProperty("dataSource.driverClassName")).thenReturn("org.sqlite.JDBC");
        when(env.getProperty("dataSource.url")).thenReturn("jdbc:sqlite:CustomerDB.sqlite");
        when(env.getProperty("entityManagerFactory.packagesToScan")).thenReturn("org.mesutormanli.customerapi.model.entity");
        when(env.getProperty("hibernate.hbm2ddl.auto")).thenReturn("update");
        when(env.getProperty("hibernate.dialect")).thenReturn("org.hibernate.community.dialect.SQLiteDialect");
        when(env.getProperty("hibernate.show_sql")).thenReturn("true");

        DataSource dataSource = dataSourceConfig.dataSource();
        LocalContainerEntityManagerFactoryBean em = jpaConfig.entityManagerFactory(dataSource);

        assertNotNull(em);
        assertNotNull(em.getDataSource());
        assertNotNull(em.getJpaVendorAdapter());
        
        // Properties check
        assertEquals("update", em.getJpaPropertyMap().get("hibernate.hbm2ddl.auto"));
        assertEquals("org.hibernate.community.dialect.SQLiteDialect", em.getJpaPropertyMap().get("hibernate.dialect"));
        assertEquals("true", em.getJpaPropertyMap().get("hibernate.show_sql"));
    }
}
