package ch.example.app.infrastructure.common.serializer

import ch.example.app.application.common.serializer.Serializer
import ch.example.app.domain.user.models.User
import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import java.util.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@SpringBootTest
class SerializerTest {

  @Autowired private lateinit var serializer: Serializer

  @Test
  fun `Serialize and deserialize a model class`() {
    val uid = UUID.randomUUID()
    val user =
      User(
        userId = UserId(uid),
        email = Email("john.doe@example.com"),
        firstName = FirstName("John"),
        lastName = LastName("Doe"),
      )

    val serializedData = serializer.serializeToBytes(user)
    println(serializer.serializeToString(user))
    val deserializedUser = serializer.deserialize(serializedData, User::class.java)

    expectThat(deserializedUser) {
      get { userId }.isEqualTo(user.userId)
      get { email }.isEqualTo(user.email)
      get { firstName }.isEqualTo(user.firstName)
      get { lastName }.isEqualTo(user.lastName)
    }
  }
}
