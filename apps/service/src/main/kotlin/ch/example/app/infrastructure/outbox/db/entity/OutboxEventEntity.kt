package ch.example.app.infrastructure.outbox.db.entity

import ch.example.app.infrastructure.configuration.persistence.TenantAwareBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "outbox_event")
class OutboxEventEntity : TenantAwareBaseEntity() {

  @Column(name = "event_type", nullable = false, length = 255) lateinit var eventType: String

  @Column(name = "aggregate_id", nullable = false) lateinit var aggregateId: UUID

  @Column(name = "event_date_utc") lateinit var eventDate: OffsetDateTime

  @Column(name = "data") lateinit var data: ByteArray
}
