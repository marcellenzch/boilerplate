package ch.example.app.persistence

import ch.example.app.config.multitenancy.TenantListener
import jakarta.persistence.*
import java.util.*

@EntityListeners(TenantListener::class)
@MappedSuperclass
abstract class TenantAwareBaseEntity : BaseEntity() {

  @Column(nullable = false) var tenantId: UUID? = null
}
