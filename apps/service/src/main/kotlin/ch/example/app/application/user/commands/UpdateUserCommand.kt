package ch.example.app.application.user.commands

import ch.example.app.domain.user.models.User
import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import ch.example.app.dto.UserCreateOrUpdateDTO
import java.util.*

data class UpdateUserCommand(
  val id: UserId,
  val email: Email = Email(),
  val firstName: FirstName = FirstName(),
  val lastName: LastName = LastName(),
) : UserDomainCommand {
  companion object

  fun toUser(): User {
    return User(userId = id, email = email, firstName = firstName, lastName = lastName)
  }
}

fun UserCreateOrUpdateDTO.toCommand(id: UUID): UpdateUserCommand {
  return UpdateUserCommand(
    id = UserId(id),
    email = Email(email),
    firstName = FirstName(firstName),
    lastName = LastName(lastName),
  )
}
