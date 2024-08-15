package ch.example.app.infrastructure.configuration.db

import ch.example.app.infrastructure.configuration.db.multitenancy.TenantAwareHikariDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration(proxyBeanMethods = false)
class MyCompleteDataSourcesConfiguration {

  @Bean
  @Primary
  @ConfigurationProperties("app.datasource.first")
  fun firstDataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  @Bean
  @Primary
  @ConfigurationProperties("app.datasource.first.configuration")
  fun firstDataSource(firstDataSourceProperties: DataSourceProperties): HikariDataSource {
    return (firstDataSourceProperties
      .initializeDataSourceBuilder()
      .type(TenantAwareHikariDataSource::class.java)
      .build())
  }

  @Bean
  @ConfigurationProperties("app.datasource.second")
  fun secondDataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  @Bean
  @ConfigurationProperties("app.datasource.second.configuration")
  fun secondDataSource(secondDataSourceProperties: DataSourceProperties): HikariDataSource {
    return secondDataSourceProperties
      .initializeDataSourceBuilder()
      .type(HikariDataSource::class.java)
      .build()
  }
}
