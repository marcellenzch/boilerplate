package ch.example.app.user.application.services

import ch.example.app.user.application.models.UsersList
import ch.example.app.user.application.queries.GetAllUsersQuery
import ch.example.app.user.application.queries.GetUserByIdQuery
import ch.example.app.user.domain.models.User

interface UserQueryService {

  fun handle(query: GetUserByIdQuery): User

  fun handle(query: GetAllUsersQuery): UsersList
}
