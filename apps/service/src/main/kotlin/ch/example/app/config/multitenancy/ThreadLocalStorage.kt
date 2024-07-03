package ch.example.app.config.multitenancy

import java.util.*

object ThreadLocalStorage {
  private val tenant = ThreadLocal<UUID>()

  var tenantId: UUID?
    get() = tenant.get()
    set(tenantName) {
      tenant.set(tenantName)
    }

  fun clear() {
    tenant.remove()
  }
}
