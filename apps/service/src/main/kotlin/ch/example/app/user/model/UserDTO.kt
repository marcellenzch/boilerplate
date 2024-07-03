package ch.example.app.user.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

class UserDTO {

  var id: UUID? = null

  @NotNull @Size(max = 120) @UserEmailUnique var email: String? = null

  @Size(max = 255) var firstName: String? = null

  @Size(max = 255) var lastName: String? = null
}
