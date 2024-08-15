package ch.example.app.application.user.commands

import ch.example.app.domain.user.models.User
import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import ch.example.app.dto.UserCreateDTO
import java.util.*

data class CreateUserCommand(
  val email: Email = Email(),
  val firstName: FirstName = FirstName(),
  val lastName: LastName = LastName(),
) : UserDomainCommand {
  companion object

  fun toUser(): User {
    return User(
      userId = UserId(value = UUID.randomUUID()),
      email = email,
      firstName = firstName,
      lastName = lastName,
    )
  }
}

fun UserCreateDTO.toCommand(): CreateUserCommand {
  return CreateUserCommand(
    email = Email(email),
    firstName = FirstName(firstName),
    lastName = LastName(lastName),
  )
}
