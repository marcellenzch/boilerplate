package ch.example.app.application.user.models

import ch.example.app.domain.user.models.User
import ch.example.app.dto.PaginationDTO
import ch.example.app.dto.UsersList200ResponseDTO
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
