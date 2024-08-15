package ch.example.app.application.user.queries

data class GetAllUsersQuery(val page: Int, val size: Int) : UserDomainQuery
