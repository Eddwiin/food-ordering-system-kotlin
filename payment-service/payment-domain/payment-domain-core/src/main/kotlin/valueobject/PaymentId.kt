package valueobject

import com.food.ordering.system.kotlin.domain.valueobject.BaseId
import java.util.*

class PaymentId(value: UUID) : BaseId<UUID>(value)