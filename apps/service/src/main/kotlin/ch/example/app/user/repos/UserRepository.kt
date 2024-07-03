package ch.example.app.user.repos

import ch.example.app.user.domain.User
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, UUID> {

    fun findAllById(id: UUID?, pageable: Pageable): Page<User>

    fun existsByEmailIgnoreCase(email: String?): Boolean

}
