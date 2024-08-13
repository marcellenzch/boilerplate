package ch.example.app.user.infrastructure.db.repository

import ch.example.app.user.application.persistence.UserRepository
import ch.example.app.user.domain.models.User
import ch.example.app.user.domain.valueObjects.Email
import ch.example.app.user.domain.valueObjects.FirstName
import ch.example.app.user.domain.valueObjects.LastName
import ch.example.app.user.domain.valueObjects.UserId
import ch.example.app.user.infrastructure.db.entity.UserEntity
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
    userId = UserId(id = id),
    email = Email(email),
    firstName = FirstName(firstName),
    lastName = LastName(lastName),
  )
}

private fun User.toEntity(): UserEntity {
  val userEntity = UserEntity()
  userEntity.id = userId.id
  userEntity.email = email.string()
  userEntity.firstName = firstName.string()
  userEntity.lastName = lastName.string()
  return userEntity
}
