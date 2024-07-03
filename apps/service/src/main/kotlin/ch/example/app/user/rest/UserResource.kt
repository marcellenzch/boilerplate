package ch.example.app.user.rest

import ch.example.app.api.UsersApi
import ch.example.app.dto.*
import ch.example.app.user.service.UserService
import java.util.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserResource(private val userService: UserService) : UsersApi {

  override fun usersList(size: Int?, page: Int?): ResponseEntity<UsersList200ResponseDTO> {
    val pageable: Pageable = PageRequest.of(page ?: 0, size ?: 10)
    val users: PagedModel<UserDTO> = userService.findAll(pageable)

    return ResponseEntity.ok(
      UsersList200ResponseDTO(
        content = users.content,
        page =
          PaginationDTO(
            number = users.metadata?.number ?: 0,
            propertySize = users.metadata?.size ?: 0,
            totalElements = users.metadata?.totalElements ?: 0,
            totalPages = users.metadata?.totalPages ?: 0,
          ),
      )
    )
  }

  override fun usersRead(id: UUID): ResponseEntity<UserDTO> {
    return ResponseEntity.ok(userService.get(id))
  }

  override fun usersCreate(userCreateDTO: UserCreateDTO): ResponseEntity<UserDTO> {
    return ResponseEntity(userService.create(userCreateDTO), HttpStatus.CREATED)
  }

  override fun usersUpdate(
    id: UUID,
    userCreateOrUpdateDTO: UserCreateOrUpdateDTO,
  ): ResponseEntity<UserDTO> {
    return ResponseEntity.ok(userService.update(id, userCreateOrUpdateDTO))
  }

  override fun usersDelete(id: UUID): ResponseEntity<Unit> {
    userService.delete(id)
    return ResponseEntity.noContent().build()
  }
}
