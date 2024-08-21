package ch.example.app.api.user.events.listeners

import ch.example.app.application.user.events.UserCreatedEvent
import ch.example.app.infrastructure.common.events.SpringApplicationEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserCreatedEventListener {

  @EventListener(
    condition =
      "#event.event.eventType == T(ch.example.app.application.user.events.UserCreatedEvent).USER_CREATED_EVENT_V1"
  )
  fun handleEvent(event: SpringApplicationEvent<UserCreatedEvent>) {
    log.info("Received user created event - ${event.getEvent().userId.value}")
  }

  companion object {
    private val log = LoggerFactory.getLogger(UserCreatedEventListener::class.java)
  }
}
