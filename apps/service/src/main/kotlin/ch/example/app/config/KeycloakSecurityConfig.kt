package ch.example.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class KeycloakSecurityConfig {

  /** Read claims from attribute realm_access.roles as SimpleGrantedAuthority. */
  private fun mapAuthorities(attributes: Map<String, Any>): List<SimpleGrantedAuthority> {
    @Suppress("unchecked_cast")
    val realmAccess =
      (attributes.getOrDefault("realm_access", emptyMap<Any, Any>()) as Map<String, Any>)
    @Suppress("unchecked_cast")
    val roles = (realmAccess.getOrDefault("roles", emptyList<Any>()) as Collection<String>)
    return roles.stream().map { role -> SimpleGrantedAuthority(role) }.toList()
  }

  @Bean
  fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
    val converter = JwtAuthenticationConverter()
    converter.setJwtGrantedAuthoritiesConverter { source -> mapAuthorities(source.claims) }
    return converter
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      cors { corsConfigurationSource() }
      csrf { disable() }
      authorizeHttpRequests {
        authorize("openapi.yaml", permitAll)
        authorize("openapi/**", permitAll)
        authorize(anyRequest, authenticated)
      }
      oauth2ResourceServer { jwt {} }
    }

    return http.build()
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val corsConfiguration = CorsConfiguration()
    corsConfiguration.allowedOrigins = listOf("http://localhost:5173")
    corsConfiguration.allowedHeaders = listOf("*")
    corsConfiguration.allowedMethods = listOf("*")
    corsConfiguration.allowCredentials = true
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", corsConfiguration)

    return source
  }
  //    @Bean
  //    fun corsConfigurationSource(): CorsConfigurationSource {
  //        val configuration = CorsConfiguration()
  //        configuration.allowedOrigins = listOf("http://localhost:5173")
  //        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
  //        val source = UrlBasedCorsConfigurationSource()
  //        source.registerCorsConfiguration("/**", configuration)
  //        return source
  //    }

}
