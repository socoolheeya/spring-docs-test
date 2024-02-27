package com.test.springdocstest.member.application.configuration

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.hibernate.cfg.Environment
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.test.springdocstest"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManger")
@EnableTransactionManagement
class JpaConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun dataSource(): DataSource =
        DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource()
        emf.setPackagesToScan("com.test.springdocstest")
        emf.jpaVendorAdapter = hibernateJpaVendorAdapter()

        return emf
    }

    private fun hibernateJpaVendorAdapter(): HibernateJpaVendorAdapter {
        val hibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        hibernateJpaVendorAdapter.setGenerateDdl(true)
        return hibernateJpaVendorAdapter;
    }

    private fun jpaProperties(): Properties {
        val properties = Properties()
        properties.setProperty(Environment.HBM2DDL_AUTO, "none")
        properties.setProperty(Environment.USE_SECOND_LEVEL_CACHE, "false")
        properties.setProperty(Environment.USE_QUERY_CACHE, "false")
        return properties;
    }



    @Bean
    fun transactionManger(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val jpaTransactionManager = JpaTransactionManager()
        jpaTransactionManager.entityManagerFactory = entityManagerFactory

        return jpaTransactionManager
    }
}