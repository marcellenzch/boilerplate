package ch.example.app.infrastructure.user.db.entity

import ch.example.app.infrastructure.configuration.persistence.TenantAwareBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "\"user\"")
class UserEntity : TenantAwareBaseEntity() {

  @Column(nullable = false, unique = true, length = 120) var email: String = ""

  @Column var firstName: String? = null

  @Column var lastName: String? = null
}
