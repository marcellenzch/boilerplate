package ch.example.app.infrastructure.common.serializer

import ch.example.app.application.common.serializer.SerializationException
import ch.example.app.application.common.serializer.Serializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SerializerImpl(private val objectMapper: ObjectMapper) : Serializer {

  override fun <T> deserialize(data: ByteArray, clazz: Class<T>): T =
    try {
      objectMapper.readValue(data, clazz)
    } catch (ex: Exception) {
      log.error("error while deserializing data: ${ex.message}, class: ${clazz.name}")
      throw SerializationException(ex, clazz)
    }

  override fun serializeToBytes(data: Any): ByteArray =
    try {
      objectMapper.writeValueAsBytes(data)
    } catch (ex: Exception) {
      log.error("error while serializing data: ${ex.message}, class: ${data::class.java.name}")
      throw SerializationException(ex, data::class.java)
    }

  override fun serializeToString(data: Any): String =
    try {
      objectMapper.writeValueAsString(data)
    } catch (ex: Exception) {
      log.error("error while serializing data: ${ex.message}, class: ${data::class.java.name}")
      throw SerializationException(ex, data::class.java)
    }

  private companion object {
    private val log = LoggerFactory.getLogger(SerializerImpl::class.java)
  }
}
