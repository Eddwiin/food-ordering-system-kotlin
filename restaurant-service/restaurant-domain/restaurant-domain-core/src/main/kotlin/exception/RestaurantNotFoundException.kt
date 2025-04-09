package exception

import com.food.ordering.system.kotlin.domain.exception.DomainException

class RestaurantNotFoundException(errorMessage: String, cause: Throwable? = null) : DomainException(errorMessage, cause)