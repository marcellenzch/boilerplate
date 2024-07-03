package ch.example.app.user.service

import ch.example.app.dto.UserCreateDTO
import ch.example.app.dto.UserCreateOrUpdateDTO
import ch.example.app.dto.UserDTO
import ch.example.app.user.domain.User
import ch.example.app.user.repos.UserRepository
import ch.example.app.util.NotFoundException
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

  fun findAll(pageable: Pageable): PagedModel<UserDTO> {
    val page: Page<User> = userRepository.findAll(pageable)

    return PagedModel(
      PageImpl(
        page.content.stream().map { user -> mapToDTO(user) }.toList(),
        pageable,
        page.totalElements,
      )
    )
  }

  fun get(id: UUID): UserDTO {
    return userRepository
      .findById(id)
      .map { user -> mapToDTO(user) }
      .orElseThrow { NotFoundException() }
  }

  fun create(userDTO: UserCreateDTO): UserDTO {
    val user = User()
    mapToEntity(userDTO, user)
    return mapToDTO(userRepository.save(user))
  }

  fun update(id: UUID, userDTO: UserCreateOrUpdateDTO): UserDTO {
    val user = userRepository.findById(id).orElseThrow { NotFoundException() }
    mapToEntity(userDTO, user)
    return mapToDTO(userRepository.save(user))
  }

  fun delete(id: UUID) {
    userRepository.deleteById(id)
  }

  private fun mapToDTO(user: User): UserDTO {
    return UserDTO(
      id = user.id,
      email = user.email,
      firstName = user.firstName,
      lastName = user.lastName,
    )
  }

  private fun mapToEntity(userDTO: UserDTO, user: User): User {
    user.email = userDTO.email
    user.firstName = userDTO.firstName
    user.lastName = userDTO.lastName
    return user
  }

  private fun mapToEntity(userDTO: UserCreateDTO, user: User): User {
    user.email = userDTO.email
    user.firstName = userDTO.firstName
    user.lastName = userDTO.lastName
    return user
  }

  private fun mapToEntity(userDTO: UserCreateOrUpdateDTO, user: User): User {
    user.email = userDTO.email
    user.firstName = userDTO.firstName
    user.lastName = userDTO.lastName
    return user
  }

  fun emailExists(email: String?): Boolean {
    return userRepository.existsByEmailIgnoreCase(email)
  }
}
