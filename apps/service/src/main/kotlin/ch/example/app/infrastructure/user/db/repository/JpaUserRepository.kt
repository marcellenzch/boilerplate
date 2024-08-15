package ch.example.app.infrastructure.user.db.repository

import ch.example.app.infrastructure.user.db.entity.UserEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
  fun findByEmail(email: String): Optional<UserEntity>
}
