package ch.example.app.user.application.queries

import ch.example.app.user.domain.valueObjects.UserId

data class GetUserByIdQuery(val userId: UserId) : UserDomainQuery
