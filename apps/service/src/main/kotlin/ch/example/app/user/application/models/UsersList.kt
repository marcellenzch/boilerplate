package ch.example.app.user.application.models

import ch.example.app.dto.PaginationDTO
import ch.example.app.dto.UsersList200ResponseDTO
import ch.example.app.user.domain.models.User
import org.springframework.data.domain.Page

data class UsersList(val page: Page<User>) {
  fun toDto(): UsersList200ResponseDTO {
    return UsersList200ResponseDTO(
      content = page.content.map { it.toDto() },
      page =
        PaginationDTO(
          number = page.number,
          propertySize = page.size,
          totalElements = page.totalElements,
          totalPages = page.totalPages,
        ),
    )
  }
}
