package ch.example.app.shared.infrastructure.persistence

import ch.example.app.shared.config.multitenancy.TenantListener
import jakarta.persistence.*
import java.util.*

@EntityListeners(TenantListener::class)
@MappedSuperclass
abstract class TenantAwareBaseEntity : BaseEntity() {

  @Column(nullable = false) var tenantId: UUID? = null
}
