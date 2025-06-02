package ru.dating.app.profileservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "ru.dating.app.profileservice.repository")
public class DataSourceConfig {

    @Value("${app.datasource.master.url}")
    private String masterURL;

    @Value("${app.datasource.master.username}")
    private String masterUsername;

    @Value("${app.datasource.master.password}")
    private String masterPassword;

    @Value("${app.datasource.master.driver-class-name}")
    private String masterDriver;

    @Value("${app.datasource.replica.url}")
    private String replicaURL;

    @Value("${app.datasource.replica.username}")
    private String replicaUsername;

    @Value("${app.datasource.replica.password}")
    private String replicaPassword;

    @Value("${app.datasource.replica.driver-class-name}")
    private String replicaDriver;

    @Bean
    @Primary
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(masterDriver)
                .url(masterURL)
                .username(masterUsername)
                .password(masterPassword)
                .build();
    }

    @Bean
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(replicaDriver)
                .url(replicaURL)
                .username(replicaUsername)
                .password(replicaPassword)
                .build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("replicaDataSource") DataSource replica) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", master);
        targetDataSources.put("replica", replica);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(master);
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource routingDataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(routingDataSource);
        emf.setPackagesToScan("ru.dating.app.profileservice.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}