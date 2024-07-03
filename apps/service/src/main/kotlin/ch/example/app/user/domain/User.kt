package ch.example.app.user.domain

import ch.example.app.persistence.TenantAwareBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "\"User\"")
class User : TenantAwareBaseEntity() {

  @Column(nullable = false, unique = true, length = 120) var email: String = ""

  @Column var firstName: String? = null

  @Column var lastName: String? = null
}
