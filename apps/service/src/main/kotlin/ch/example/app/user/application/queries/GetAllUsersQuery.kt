package ch.example.app.user.application.queries

data class GetAllUsersQuery(val page: Int, val size: Int) : UserDomainQuery
