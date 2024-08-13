package ch.example.app.user.domain.models

import ch.example.app.dto.UserDTO
import ch.example.app.user.domain.valueObjects.Email
import ch.example.app.user.domain.valueObjects.FirstName
import ch.example.app.user.domain.valueObjects.LastName
import ch.example.app.user.domain.valueObjects.UserId

class User(val userId: UserId) {
  var email: Email = Email()
    private set

  var firstName: FirstName = FirstName()
    private set

  var lastName: LastName = LastName()
    private set

  constructor(
    userId: UserId,
    email: Email = Email(),
    firstName: FirstName = FirstName(),
    lastName: LastName = LastName(),
  ) : this(userId) {
    this.email = email
    this.firstName = firstName
    this.lastName = lastName
  }

  fun toDto(): UserDTO {
    return UserDTO(
      id = userId.id,
      email = email.string(),
      firstName = firstName.string(),
      lastName = lastName.string(),
    )
  }
}
