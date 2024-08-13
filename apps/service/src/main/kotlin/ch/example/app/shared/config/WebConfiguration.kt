package ch.example.app.shared.config

import ch.example.app.shared.config.multitenancy.TenantInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(private val tenantInterceptor: TenantInterceptor) : WebMvcConfigurer {
  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addWebRequestInterceptor(tenantInterceptor)
  }
}
