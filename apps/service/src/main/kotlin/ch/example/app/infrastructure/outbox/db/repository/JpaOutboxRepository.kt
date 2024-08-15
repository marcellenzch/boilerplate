package ch.example.app.infrastructure.outbox.db.repository

import ch.example.app.infrastructure.outbox.db.entity.OutboxEventEntity
import jakarta.persistence.LockModeType
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface JpaOutboxRepository : JpaRepository<OutboxEventEntity, UUID> {
  @Transactional
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select oe from OutboxEventEntity oe where oe.id = :id")
  fun findOneForUpdate(id: UUID): OutboxEventEntity?
}
