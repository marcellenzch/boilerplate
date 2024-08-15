package ch.example.app.application.outbox.persistance

import ch.example.app.domain.outbox.models.OutboxEvent

interface OutboxRepository {

  fun save(event: OutboxEvent): OutboxEvent

  suspend fun deleteWithLock(
    event: OutboxEvent,
    callback: suspend (event: OutboxEvent) -> Unit,
  ): OutboxEvent
}
