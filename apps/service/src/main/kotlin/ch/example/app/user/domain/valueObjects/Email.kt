package ch.example.app.user.domain.valueObjects

import java.util.*

@JvmInline
value class Email(val email: String? = "") {
  fun string() = email.toString() ?: ""
}
