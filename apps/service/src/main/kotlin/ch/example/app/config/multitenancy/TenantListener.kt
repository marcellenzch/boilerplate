package ch.example.app.config.multitenancy

import ch.example.app.persistence.TenantAwareBaseEntity
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
