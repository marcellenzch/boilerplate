package ch.example.app.api.user.controller

import ch.example.app.api.UsersApi
import ch.example.app.application.user.commands.DeleteUserCommand
import ch.example.app.application.user.commands.toCommand
import ch.example.app.application.user.queries.GetAllUsersQuery
import ch.example.app.application.user.queries.GetUserByIdQuery
import ch.example.app.application.user.services.UserCommandService
import ch.example.app.application.user.services.UserQueryService
import ch.example.app.domain.user.valueObjects.UserId
import ch.example.app.dto.*
import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UsersController(
  private val userCommandService: UserCommandService,
  private val userQueryService: UserQueryService,
) : UsersApi {

  override fun usersList(
    xTenantId: UUID,
    size: Int?,
    page: Int?,
  ): ResponseEntity<UsersList200ResponseDTO> {
    val users = userQueryService.handle(GetAllUsersQuery(page ?: 0, size ?: 10))
    return ResponseEntity.ok(users.toDto())
  }

  override fun usersRead(xTenantId: UUID, id: UUID): ResponseEntity<UserDTO> {
    return ResponseEntity.ok(userQueryService.handle(GetUserByIdQuery(UserId(id))).toDto())
  }

  override fun usersCreate(xTenantId: UUID, userCreateDTO: UserCreateDTO): ResponseEntity<UserDTO> {
    val user = userCommandService.handle(userCreateDTO.toCommand())
    return ResponseEntity(user.toDto(), HttpStatus.CREATED)
  }

  override fun usersUpdate(
    xTenantId: UUID,
    id: UUID,
    userCreateOrUpdateDTO: UserCreateOrUpdateDTO,
  ): ResponseEntity<UserDTO> {
    val user = userCommandService.handle(userCreateOrUpdateDTO.toCommand(id))
    return ResponseEntity.ok(user.toDto())
  }

  override fun usersDelete(xTenantId: UUID, id: UUID): ResponseEntity<Unit> {
    userCommandService.handle(DeleteUserCommand(id))
    return ResponseEntity.noContent().build()
  }
}
