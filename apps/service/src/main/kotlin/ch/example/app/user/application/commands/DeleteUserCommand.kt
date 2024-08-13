package ch.example.app.user.application.commands

import java.util.*

data class DeleteUserCommand(val id: UUID) : UserDomainCommand {
  companion object
}
