package ch.example.app.user.domain.valueObjects

@JvmInline
value class FirstName(val email: String? = "") {
  fun string() = email?.toString() ?: ""
}
