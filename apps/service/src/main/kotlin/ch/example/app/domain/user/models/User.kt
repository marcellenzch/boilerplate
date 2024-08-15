package ch.example.app.domain.user.models

import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import ch.example.app.dto.UserDTO

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
      id = userId.value,
      email = email.value,
      firstName = firstName.value,
      lastName = lastName.value,
    )
  }
}
