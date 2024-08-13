package ch.example.app.user.application.services

import ch.example.app.user.application.commands.CreateUserCommand
import ch.example.app.user.application.commands.DeleteUserCommand
import ch.example.app.user.application.commands.UpdateUserCommand
import ch.example.app.user.application.persistence.UserRepository
import ch.example.app.user.domain.exceptions.EmailAlreadyExistsException
import ch.example.app.user.domain.exceptions.UserNotFoundException
import ch.example.app.user.domain.models.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserCommandServiceImpl(private val userRepository: UserRepository) : UserCommandService {
  var log: Logger = LoggerFactory.getLogger(UserCommandServiceImpl::class.java)

  override fun handle(command: CreateUserCommand): User {
    val user: User = command.toUser()
    log.info("Creating user: ${user.email.string()}")

    userRepository.findByEmail(user.email.string()).ifPresent {
      throw EmailAlreadyExistsException("User with email ${user.email.string()} already exists")
    }

    return userRepository.save(user)
  }

  override fun handle(command: UpdateUserCommand): User {
    val existingUser: User =
      userRepository.findById(command.id.id).orElseThrow {
        UserNotFoundException("Could not find user with id ${command.id}")
      }

    if (!existingUser.email.string().equals(command.email.string())) {
      userRepository.findByEmail(command.email.string()).ifPresent {
        throw EmailAlreadyExistsException(
          "User with email ${command.email.string()} already exists"
        )
      }
    }

    log.info("Updating user: ${command.email.string()}")

    return userRepository.save(command.toUser())
  }

  override fun handle(command: DeleteUserCommand) {
    val userEntity =
      userRepository.findById(command.id).orElseThrow {
        UserNotFoundException("Could not find user with id ${command.id} ")
      }

    log.info("Deleting user: ${userEntity.email}")
    userRepository.delete(userEntity)
  }
}
