package ch.example.app.infrastructure.common.events

import ch.example.app.application.user.events.DomainEvent
import org.springframework.context.ApplicationEvent

class SpringApplicationEvent<T>(entity: DomainEvent) : ApplicationEvent(entity) {}
