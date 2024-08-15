package ch.example.app.infrastructure.configuration.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidTenantIdException(msg: String) : RuntimeException(msg) {}
