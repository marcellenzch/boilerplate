package ch.example.app.user.infrastructure.db.entity

import ch.example.app.shared.infrastructure.persistence.TenantAwareBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "\"User\"")
class UserEntity : TenantAwareBaseEntity() {

  @Column(nullable = false, unique = true, length = 120) var email: String = ""

  @Column var firstName: String? = null

  @Column var lastName: String? = null
}
