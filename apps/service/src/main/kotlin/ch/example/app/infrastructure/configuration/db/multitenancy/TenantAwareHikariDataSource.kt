package ch.example.app.infrastructure.configuration.db.multitenancy

import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException
import java.util.*

class TenantAwareHikariDataSource : HikariDataSource() {

  @Throws(SQLException::class)
  override fun getConnection(): Connection {
    val connection = super.getConnection()
    return setTenantId(connection)
  }

  @Throws(SQLException::class)
  override fun getConnection(username: String, password: String): Connection {
    val connection = super.getConnection(username, password)
    return setTenantId(connection)
  }

  private fun setTenantId(connection: Connection): Connection {
    val tenantId: UUID = ThreadLocalStorage.tenantId ?: return connection

    connection.prepareStatement("select set_config('app.tenant_id', ?, false)").use { statement ->
      statement.setString(1, tenantId.toString())
      statement.execute()
    }

    return connection
  }
}
