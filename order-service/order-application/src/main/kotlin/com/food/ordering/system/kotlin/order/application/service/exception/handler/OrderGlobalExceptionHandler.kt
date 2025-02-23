package com.food.ordering.system.kotlin.order.application.service.exception.handler

import com.food.ordering.system.kotlin.application.handler.ErrorDTO
import com.food.ordering.system.kotlin.application.handler.GlobalExceptionHandler
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class OrderGlobalExceptionHandler : GlobalExceptionHandler() {
    private val logger = KotlinLogging.logger {}

    @ResponseBody
    @ExceptionHandler(OrderDomainException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(orderDomainException: OrderDomainException): ErrorDTO {
        logger.error { "${orderDomainException.message}" }

        return ErrorDTO.builder()
            .code(HttpStatus.BAD_REQUEST.reasonPhrase)
            .message(orderDomainException.message)
            .build()
    }

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(orderNotFoundException: OrderNotFoundException): ErrorDTO {
        logger.error { "${orderNotFoundException.message}" }

        return ErrorDTO.builder()
            .code(HttpStatus.NOT_FOUND.reasonPhrase)
            .message(orderNotFoundException.message)
            .build()
    }
}