package ch.example.app.application.user.services

import ch.example.app.application.common.publisher.EventPublisher
import ch.example.app.application.common.serializer.Serializer
import ch.example.app.application.outbox.persistance.OutboxRepository
import ch.example.app.application.user.commands.CreateUserCommand
import ch.example.app.application.user.commands.DeleteUserCommand
import ch.example.app.application.user.commands.UpdateUserCommand
import ch.example.app.application.user.events.toUserCreatedEvent
import ch.example.app.application.user.persistence.UserRepository
import ch.example.app.domain.outbox.models.OutboxEvent
import ch.example.app.domain.user.exceptions.EmailAlreadyExistsException
import ch.example.app.domain.user.exceptions.UserNotFoundException
import ch.example.app.domain.user.models.User
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Service
class UserCommandServiceImpl(
  private val userRepository: UserRepository,
  private val outboxRepository: OutboxRepository,
  private val serializer: Serializer,
  private val eventPublisher: EventPublisher,
  private val transactionManager: PlatformTransactionManager,
) : UserCommandService {

  override fun handle(command: CreateUserCommand): User {
    val user: User = command.toUser()

    log.info("Creating user: ${user.email.value}")

    userRepository.findByEmail(user.email.value).ifPresent {
      throw EmailAlreadyExistsException("User with email ${user.email.value} already exists")
    }

    val (createdUser, event) =
      transactionTemplate.execute {
        val createdUser = userRepository.save(command.toUser())
        val event = outboxRepository.save(createdUser.toUserCreatedEvent(serializer))

        return@execute createdUser to event
      } ?: throw RuntimeException("Error while creating user")

    publisherScope.launch { publishOutboxEvent(event) }
    log.info("publisherscope launched")

    return createdUser
  }

  override fun handle(command: UpdateUserCommand): User {
    val existingUser: User =
      userRepository.findById(command.id.value).orElseThrow {
        UserNotFoundException("Could not find user with id ${command.id}")
      }

    if (existingUser.email.value != command.email.value) {
      userRepository.findByEmail(command.email.value).ifPresent {
        throw EmailAlreadyExistsException("User with email ${command.email.value} already exists")
      }
    }

    log.info("Updating user: ${command.email.value}")

    val user = userRepository.save(command.toUser())

    return user
  }

  override fun handle(command: DeleteUserCommand) {
    val userEntity =
      userRepository.findById(command.id).orElseThrow {
        UserNotFoundException("Could not find user with id ${command.id} ")
      }

    log.info("Deleting user: ${userEntity.email}")
    userRepository.delete(userEntity)
  }

  private suspend fun publishOutboxEvent(event: OutboxEvent): OutboxEvent {
    try {
      val deletedEvent = outboxRepository.deleteWithLock(event) { eventPublisher.publish(event) }
      log.info("Outbox event has been published and deleted: $event")
      return deletedEvent
    } catch (e: Exception) {
      log.error("Error while publishing outbox event: ${event.eventId}, error: $e")
      throw e
    }
  }

  private val transactionTemplate = TransactionTemplate(transactionManager)
  private val publisherScope =
    CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName(this::class.java.name))

  private companion object {
    private val log = LoggerFactory.getLogger(UserCommandServiceImpl::class.java)
  }
}
