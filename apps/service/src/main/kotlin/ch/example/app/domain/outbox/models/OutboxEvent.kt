package ch.example.app.domain.outbox.models

import java.time.OffsetDateTime
import java.util.*

data class OutboxEvent(
  val eventId: UUID,
  val eventType: String,
  val aggregateId: UUID,
  val data: ByteArray,
  val timestamp: OffsetDateTime,
) {
  companion object {}

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OutboxEvent

    if (eventId != other.eventId) return false
    if (eventType != other.eventType) return false
    if (timestamp != other.timestamp) return false
    if (aggregateId != other.aggregateId) return false

    return true
  }

  /**
   * The value 31 was chosen because it is an odd prime. If it were even and the multiplication
   * overflowed, information would be lost, as multiplication by 2 is equivalent to shifting. The
   * advantage of using a prime is less clear, but it is traditional. A nice property of 31 is that
   * the multiplication can be replaced by a shift and a subtraction for better performance: 31 * i
   * == (i << 5) - i. Modern VMs do this sort of optimization automatically.
   *
   * Source: https://stackoverflow.com/a/299748
   */
  override fun hashCode(): Int {
    var result = eventId.hashCode()
    result = 31 * result + eventType.hashCode()
    result = 31 * result + timestamp.hashCode()
    result = 31 * result + aggregateId.hashCode()
    return result
  }
}
