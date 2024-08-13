package ch.example.app.user.domain.valueObjects

import java.util.*

@JvmInline
value class UserId(val id: UUID) {
  fun string() = id.toString() ?: ""
}
