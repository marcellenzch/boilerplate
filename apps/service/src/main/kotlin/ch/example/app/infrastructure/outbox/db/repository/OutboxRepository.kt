package ch.example.app.infrastructure.outbox.db.repository

import ch.example.app.application.outbox.persistance.OutboxRepository
import ch.example.app.domain.outbox.models.OutboxEvent
import ch.example.app.infrastructure.outbox.db.entity.OutboxEventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Repository
class OutboxRepositoryImpl(private val jpaOutboxRepository: JpaOutboxRepository) :
  OutboxRepository {
  override fun save(event: OutboxEvent): OutboxEvent {
    return jpaOutboxRepository.save(event.toEntity()).toOutboxEvent()
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  override suspend fun deleteWithLock(
    event: OutboxEvent,
    callback: suspend (event: OutboxEvent) -> Unit,
  ): OutboxEvent {
    runBlocking {
      withTimeout(LOCK_BLOCKING_TIMEOUT_MS) {
        val outBoxEvent =
          withContext(Dispatchers.IO) { jpaOutboxRepository.findOneForUpdate(event.eventId) }
            ?: throw RuntimeException("Event not found")

        callback(event)
        deleteOutBoxEvent(outBoxEvent)
      }
    }

    return event
  }

  fun deleteOutBoxEvent(outboxEvent: OutboxEventEntity) {
    jpaOutboxRepository.delete(outboxEvent)
  }

  private companion object {
    const val LOCK_BLOCKING_TIMEOUT_MS = 10000L
  }
}

private fun OutboxEventEntity.toOutboxEvent(): OutboxEvent {
  return OutboxEvent(
    eventId = id,
    eventType = eventType,
    aggregateId = aggregateId,
    data = data,
    timestamp = eventDate,
  )
}

private fun OutboxEvent.toEntity(): OutboxEventEntity {
  val outboxEventEntity = OutboxEventEntity()
  outboxEventEntity.id = eventId
  outboxEventEntity.eventType = eventType
  outboxEventEntity.aggregateId = aggregateId
  outboxEventEntity.data = data
  outboxEventEntity.eventDate = timestamp
  return outboxEventEntity
}
