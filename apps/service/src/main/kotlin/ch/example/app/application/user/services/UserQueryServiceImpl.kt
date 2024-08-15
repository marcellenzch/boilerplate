package ch.example.app.application.user.services

import ch.example.app.application.user.models.UsersList
import ch.example.app.application.user.persistence.UserRepository
import ch.example.app.application.user.queries.GetAllUsersQuery
import ch.example.app.application.user.queries.GetUserByIdQuery
import ch.example.app.domain.user.exceptions.UserNotFoundException
import ch.example.app.domain.user.models.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserQueryServiceImpl(private val userRepository: UserRepository) : UserQueryService {
  override fun handle(query: GetUserByIdQuery): User {
    return userRepository.findById(query.userId.value).orElseThrow {
      UserNotFoundException("could not find user with id ${query.userId.value}")
    }
  }

  override fun handle(query: GetAllUsersQuery): UsersList {
    val pageable: Pageable = PageRequest.of(query.page, query.size)
    return UsersList(userRepository.findAll(pageable))
  }
}
