package ch.example.app.user.application.persistence

import ch.example.app.user.domain.models.User
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserRepository {
  fun save(user: User): User

  fun delete(user: User)

  fun deleteById(id: UUID)

  fun findAll(pageable: Pageable): Page<User>

  fun findById(id: UUID): Optional<User>

  fun findByEmail(email: String): Optional<User>
}
