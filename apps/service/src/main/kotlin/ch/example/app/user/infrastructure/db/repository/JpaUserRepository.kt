package ch.example.app.user.infrastructure.db.repository

import ch.example.app.user.infrastructure.db.entity.UserEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
  fun findByEmail(email: String): Optional<UserEntity>
}
