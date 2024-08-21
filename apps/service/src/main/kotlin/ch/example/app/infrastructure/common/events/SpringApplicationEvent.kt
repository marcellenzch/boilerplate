package ch.example.app.infrastructure.common.events

import ch.example.app.application.user.events.DomainEvent
import org.springframework.context.ApplicationEvent

class SpringApplicationEvent<T : DomainEvent>(private val entity: T) : ApplicationEvent(entity) {
  fun getEvent(): T {
    return entity
  }
}
