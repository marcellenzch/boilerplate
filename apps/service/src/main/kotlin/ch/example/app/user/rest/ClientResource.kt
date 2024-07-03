package ch.example.app.user.rest

import ch.example.app.api.ClientsApi
import ch.example.app.dto.ClientDTO
import ch.example.app.dto.ClientsList200ResponseDTO
import ch.example.app.dto.PaginationDTO
import ch.example.app.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ClientsApiDelegateImpl(private val userService: UserService) : ClientsApi {
  override fun clientsList(size: Int?, page: Int?): ResponseEntity<ClientsList200ResponseDTO> {
    return ResponseEntity(
      ClientsList200ResponseDTO(
        page = PaginationDTO(propertySize = 10, number = 2, totalElements = 2, totalPages = 4),
        content =
          listOf(
            ClientDTO("1185ae16-e8e8-4d90-a5f1-c254ab20c382", "Company Ltd."),
            ClientDTO("1185ae16-e8e8-4d90-a5f1-c254ab20c383", "abc def"),
          ),
      ),
      HttpStatus.OK,
    )
  }
}
