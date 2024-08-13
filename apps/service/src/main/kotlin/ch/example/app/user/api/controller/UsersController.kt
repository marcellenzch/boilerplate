package ch.example.app.user.api.controller

import ch.example.app.api.UsersApi
import ch.example.app.dto.*
import ch.example.app.user.application.commands.DeleteUserCommand
import ch.example.app.user.application.commands.toCommand
import ch.example.app.user.application.queries.GetAllUsersQuery
import ch.example.app.user.application.queries.GetUserByIdQuery
import ch.example.app.user.application.services.UserCommandService
import ch.example.app.user.application.services.UserQueryService
import ch.example.app.user.domain.valueObjects.UserId
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

  override fun usersList(size: Int?, page: Int?): ResponseEntity<UsersList200ResponseDTO> {
    val users = userQueryService.handle(GetAllUsersQuery(page ?: 0, size ?: 10))
    return ResponseEntity.ok(users.toDto())
  }

  override fun usersRead(id: UUID): ResponseEntity<UserDTO> {
    return ResponseEntity.ok(userQueryService.handle(GetUserByIdQuery(UserId(id))).toDto())
  }

  override fun usersCreate(userCreateDTO: UserCreateDTO): ResponseEntity<UserDTO> {
    val user = userCommandService.handle(userCreateDTO.toCommand())
    return ResponseEntity(user.toDto(), HttpStatus.CREATED)
  }

  override fun usersUpdate(
    id: UUID,
    userCreateOrUpdateDTO: UserCreateOrUpdateDTO,
  ): ResponseEntity<UserDTO> {
    val user = userCommandService.handle(userCreateOrUpdateDTO.toCommand(id))
    return ResponseEntity.ok(user.toDto())
  }

  override fun usersDelete(id: UUID): ResponseEntity<Unit> {
    userCommandService.handle(DeleteUserCommand(id))
    return ResponseEntity.noContent().build()
  }
}
