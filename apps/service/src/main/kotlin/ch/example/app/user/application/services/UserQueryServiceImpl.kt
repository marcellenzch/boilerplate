package ch.example.app.user.application.services

import ch.example.app.user.application.models.UsersList
import ch.example.app.user.application.persistence.UserRepository
import ch.example.app.user.application.queries.GetAllUsersQuery
import ch.example.app.user.application.queries.GetUserByIdQuery
import ch.example.app.user.domain.exceptions.UserNotFoundException
import ch.example.app.user.domain.models.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserQueryServiceImpl(private val userRepository: UserRepository) : UserQueryService {
  override fun handle(query: GetUserByIdQuery): User {
    return userRepository.findById(query.userId.id).orElseThrow {
      UserNotFoundException("could not find user with id ${query.userId.id}")
    }
  }

  override fun handle(query: GetAllUsersQuery): UsersList {
    val pageable: Pageable = PageRequest.of(query.page, query.size)
    return UsersList(userRepository.findAll(pageable))
  }
}
