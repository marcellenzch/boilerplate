package ch.example.app.application.user.queries

import ch.example.app.domain.user.valueObjects.UserId

data class GetUserByIdQuery(val userId: UserId) : UserDomainQuery
