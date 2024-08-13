package ch.example.app.user.domain.valueObjects

@JvmInline
value class LastName(val email: String? = "") {
  fun string() = email?.toString() ?: ""
}
