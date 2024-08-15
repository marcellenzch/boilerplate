package ch.example.app.infrastructure.configuration.persistence

import ch.example.app.infrastructure.configuration.db.multitenancy.TenantListener
import jakarta.persistence.*
import java.util.*

@EntityListeners(TenantListener::class)
@MappedSuperclass
abstract class TenantAwareBaseEntity : BaseEntity() {

  @Column(nullable = false) var tenantId: UUID? = null
}
