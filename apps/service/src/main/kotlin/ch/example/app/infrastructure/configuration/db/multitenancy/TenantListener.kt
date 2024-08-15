package ch.example.app.infrastructure.configuration.db.multitenancy

import ch.example.app.infrastructure.configuration.persistence.TenantAwareBaseEntity
import jakarta.persistence.PrePersist
import jakarta.persistence.PreRemove
import jakarta.persistence.PreUpdate

class TenantListener {
  @PreUpdate
  @PreRemove
  @PrePersist
  fun setTenant(entity: TenantAwareBaseEntity) {
    entity.tenantId = ThreadLocalStorage.tenantId
  }
}
