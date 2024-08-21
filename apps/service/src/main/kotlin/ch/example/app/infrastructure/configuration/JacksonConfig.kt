package ch.example.app.infrastructure.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
@EnableSpringDataWebSupport(
  pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
class JacksonConfig {

  @Bean
  fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
    val objectMapper = builder.build<ObjectMapper>()
    objectMapper.registerKotlinModule()
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false)
    return objectMapper
  }
}
