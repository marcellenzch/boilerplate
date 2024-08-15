package ch.example.app.application.user.events

import ch.example.app.application.common.serializer.Serializer
import ch.example.app.application.user.events.UserCreatedEvent.Companion.USER_CREATED_EVENT_V1
import ch.example.app.domain.outbox.models.OutboxEvent
import ch.example.app.domain.user.models.User
import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

data class UserCreatedEvent(
  val userId: UserId,
  val email: Email,
  val firstName: FirstName,
  val lastName: LastName,
  override val eventId: UUID,
  override val eventType: String,
  override val timestamp: OffsetDateTime,
  override val aggregateId: UUID,
) : DomainEvent {
  companion object {
    const val USER_CREATED_EVENT_V1 = "USER_CREATED_EVENT_V1"
  }
}

fun UserCreatedEvent.toOutboxEvent(serializer: Serializer) =
  OutboxEvent(
    eventId = UUID.randomUUID(),
    eventType = USER_CREATED_EVENT_V1,
    timestamp = OffsetDateTime.now(ZoneOffset.UTC),
    data = serializer.serializeToBytes(this),
    aggregateId = aggregateId,
  )

fun UserCreatedEvent.toUser() =
  User(userId = userId, email = email, firstName = firstName, lastName = lastName)

fun User.toUserCreatedEvent(): UserCreatedEvent {
  return UserCreatedEvent(
    userId = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    eventId = UUID.randomUUID(),
    eventType = USER_CREATED_EVENT_V1,
    timestamp = OffsetDateTime.now(ZoneOffset.UTC),
    aggregateId = userId.value,
  )
}

fun User.toUserCreatedEvent(serializer: Serializer): OutboxEvent {
  return this.toUserCreatedEvent().toOutboxEvent(serializer)
}
