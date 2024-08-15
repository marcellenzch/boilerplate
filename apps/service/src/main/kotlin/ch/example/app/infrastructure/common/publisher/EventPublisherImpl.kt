package ch.example.app.infrastructure.common.publisher

import ch.example.app.application.common.publisher.EventPublisher
import ch.example.app.application.common.serializer.Serializer
import ch.example.app.application.user.events.DomainEvent
import ch.example.app.application.user.events.UserCreatedEvent
import ch.example.app.domain.outbox.models.OutboxEvent
import ch.example.app.infrastructure.common.events.SpringApplicationEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisherImpl(
  private val applicationEventPublisher: ApplicationEventPublisher,
  private val serializer: Serializer,
) : EventPublisher {

  override suspend fun publish(event: OutboxEvent) {
    applicationEventPublisher.publishEvent(
      SpringApplicationEvent<DomainEvent>(
        serializer.deserialize(event.data, event.serializationClazz())
      )
    )
    log.info("Published event: $event")
  }

  companion object {
    private val log = LoggerFactory.getLogger(EventPublisherImpl::class.java)
  }
}

internal fun OutboxEvent.serializationClazz() =
  when (eventType) {
    UserCreatedEvent.USER_CREATED_EVENT_V1 -> UserCreatedEvent::class.java
    else -> throw RuntimeException("Unknown event type: $eventType")
  }
