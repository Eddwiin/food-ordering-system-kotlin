package com.food.ordering.system.kotlin.application.handler

class ErrorDTO(
    val code: String,
    val message: String?,
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var code: String = ""
        private var message: String? = ""

        fun code(code: String) = apply { this.code = code }
        fun message(message: String?) = apply { this.message = message }

        fun build(): ErrorDTO {
            return ErrorDTO(code, message)
        }
    }
}
