package ch.example.app.application.user.commands

import java.util.*

data class DeleteUserCommand(val id: UUID) : UserDomainCommand {
  companion object
}
