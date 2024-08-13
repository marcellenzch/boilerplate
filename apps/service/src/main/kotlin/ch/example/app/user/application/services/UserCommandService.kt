package ch.example.app.user.application.services

import ch.example.app.user.application.commands.CreateUserCommand
import ch.example.app.user.application.commands.DeleteUserCommand
import ch.example.app.user.application.commands.UpdateUserCommand
import ch.example.app.user.domain.models.User

interface UserCommandService {
  fun handle(command: CreateUserCommand): User

  fun handle(command: UpdateUserCommand): User

  fun handle(command: DeleteUserCommand)
}
