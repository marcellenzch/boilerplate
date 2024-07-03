package ch.example.app.persistence

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue
  @UuidGenerator
  var id: UUID = UUID.randomUUID()

  @CreatedDate @Column(nullable = false, updatable = false) var dateCreated: OffsetDateTime? = null

  @LastModifiedDate @Column(nullable = false) var lastUpdated: OffsetDateTime? = null
}
