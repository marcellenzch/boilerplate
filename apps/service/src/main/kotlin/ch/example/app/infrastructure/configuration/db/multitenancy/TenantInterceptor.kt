package ch.example.app.infrastructure.configuration.db.multitenancy

import ch.example.app.infrastructure.configuration.exceptions.InvalidTenantIdException
import jakarta.validation.Valid
import java.util.UUID
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.WebRequestInterceptor

@Component
class TenantInterceptor : WebRequestInterceptor {

  companion object {
    const val X_TENANT_ID: String = "X-TENANT-ID"
  }

  override fun preHandle(request: WebRequest) {
    try {
      setTenantId(UUID.fromString(request.getHeader(X_TENANT_ID).orEmpty()))
    } catch (e: IllegalArgumentException) {
      ThreadLocalStorage.clear()
      throw InvalidTenantIdException("Invalid tenant ID")
    }
  }

  private fun setTenantId(@Valid tenantId: UUID) {
    ThreadLocalStorage.tenantId = tenantId
  }

  override fun postHandle(@NonNull request: WebRequest, model: ModelMap?) {
    ThreadLocalStorage.clear()
  }

  override fun afterCompletion(@NonNull request: WebRequest, ex: Exception?) {
    ThreadLocalStorage.tenantId = null
  }
}
