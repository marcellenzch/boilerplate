package ch.example.app.application.common.publisher

import ch.example.app.domain.outbox.models.OutboxEvent

interface EventPublisher {

  suspend fun publish(event: OutboxEvent): Unit
}
