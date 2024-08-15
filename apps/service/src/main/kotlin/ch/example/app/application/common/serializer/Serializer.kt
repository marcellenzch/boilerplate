package ch.example.app.application.common.serializer

interface Serializer {

  fun <T> deserialize(data: ByteArray, clazz: Class<T>): T

  fun serializeToBytes(data: Any): ByteArray

  fun serializeToString(data: Any): String
}
