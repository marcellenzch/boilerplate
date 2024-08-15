package ch.example.app.application.user.events

import java.time.OffsetDateTime
import java.util.UUID

sealed interface DomainEvent {
  val eventId: UUID
  val eventType: String
  val timestamp: OffsetDateTime
  val aggregateId: UUID
}
