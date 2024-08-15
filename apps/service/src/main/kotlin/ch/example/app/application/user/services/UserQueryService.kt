package ch.example.app.application.user.services

import ch.example.app.application.user.models.UsersList
import ch.example.app.application.user.queries.GetAllUsersQuery
import ch.example.app.application.user.queries.GetUserByIdQuery
import ch.example.app.domain.user.models.User

interface UserQueryService {

  fun handle(query: GetUserByIdQuery): User

  fun handle(query: GetAllUsersQuery): UsersList
}
