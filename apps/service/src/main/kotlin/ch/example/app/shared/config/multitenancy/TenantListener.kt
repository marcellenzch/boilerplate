package ch.example.app.shared.config.multitenancy

import ch.example.app.shared.infrastructure.persistence.TenantAwareBaseEntity
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
