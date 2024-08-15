package ch.example.app.api.user.events.listeners

import ch.example.app.application.user.events.UserCreatedEvent
import ch.example.app.infrastructure.common.events.SpringApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class CustomSpringEventListener : ApplicationListener<SpringApplicationEvent<UserCreatedEvent>> {
  override fun onApplicationEvent(event: SpringApplicationEvent<UserCreatedEvent>) {
    println("Received spring custom event - $event")
  }
}
