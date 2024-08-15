package ch.example.app.application.user.services

import ch.example.app.application.user.commands.CreateUserCommand
import ch.example.app.application.user.commands.DeleteUserCommand
import ch.example.app.application.user.commands.UpdateUserCommand
import ch.example.app.domain.user.models.User

interface UserCommandService {
  fun handle(command: CreateUserCommand): User

  fun handle(command: UpdateUserCommand): User

  fun handle(command: DeleteUserCommand)
}
