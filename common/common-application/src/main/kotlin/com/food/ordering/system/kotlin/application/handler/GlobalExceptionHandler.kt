package com.food.ordering.system.kotlin.application.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
open class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ResponseBody
    @ExceptionHandler(value = [(Exception::class)])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): ErrorDTO {
        logger.error { exception.message }

        return ErrorDTO.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
            .message("Unexception message!")
            .build()
    }

    @ResponseBody
    @ExceptionHandler(value = [(ValidationException::class)])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(validationException: ValidationException): ErrorDTO {
        if (validationException is ConstraintViolationException) {
            val violation =
                extractViolationsFromException((ConstraintViolationException::class.java).cast(validationException))

            logger.error { violation }
            logger.error { validationException }

            return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.reasonPhrase)
                .message(violation)
                .build()
        } else {
            val errorMessage = validationException.message
            logger.error { errorMessage }
            logger.error { validationException }
            return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.reasonPhrase)
                .message(errorMessage)
                .build()
        }
    }

    private fun extractViolationsFromException(validationException: ConstraintViolationException): String {
        return validationException.constraintViolations.map { it.message }
            .joinToString("--")
    }
}