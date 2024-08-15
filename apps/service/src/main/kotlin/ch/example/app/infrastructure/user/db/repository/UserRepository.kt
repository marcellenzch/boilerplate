package ch.example.app.infrastructure.user.db.repository

import ch.example.app.application.user.persistence.UserRepository
import ch.example.app.domain.user.models.User
import ch.example.app.domain.user.valueObjects.Email
import ch.example.app.domain.user.valueObjects.FirstName
import ch.example.app.domain.user.valueObjects.LastName
import ch.example.app.domain.user.valueObjects.UserId
import ch.example.app.infrastructure.user.db.entity.UserEntity
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val jpaUserRepository: JpaUserRepository) : UserRepository {

  override fun save(user: User): User {
    return jpaUserRepository.save(user.toEntity()).toUser()
  }

  override fun delete(user: User) {
    jpaUserRepository.delete(user.toEntity())
  }

  override fun deleteById(id: UUID) {
    jpaUserRepository.deleteById(id)
  }

  override fun findById(id: UUID): Optional<User> {
    return jpaUserRepository.findById(id).map { Optional.of(it.toUser()) }.orElse(Optional.empty())
  }

  override fun findByEmail(email: String): Optional<User> {
    return jpaUserRepository
      .findByEmail(email)
      .map { Optional.of(it.toUser()) }
      .orElse(Optional.empty())
  }

  override fun findAll(pageable: Pageable): Page<User> {
    return jpaUserRepository.findAll(pageable).map { it.toUser() }
  }
}

private fun UserEntity.toUser(): User {
  return User(
    userId = UserId(id),
    email = Email(email),
    firstName = FirstName(firstName),
    lastName = LastName(lastName),
  )
}

private fun User.toEntity(): UserEntity {
  val userEntity = UserEntity()
  userEntity.id = userId.value
  userEntity.email = email.value
  userEntity.firstName = firstName.value
  userEntity.lastName = lastName.value
  return userEntity
}
