package ch.example.app.user.application.commands

import ch.example.app.dto.UserCreateOrUpdateDTO
import ch.example.app.user.domain.models.User
import ch.example.app.user.domain.valueObjects.Email
import ch.example.app.user.domain.valueObjects.FirstName
import ch.example.app.user.domain.valueObjects.LastName
import ch.example.app.user.domain.valueObjects.UserId
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
