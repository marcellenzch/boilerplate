package ch.example.app.domain.user.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(msg: String) : RuntimeException(msg) {}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EmailAlreadyExistsException(msg: String) : RuntimeException(msg) {}
