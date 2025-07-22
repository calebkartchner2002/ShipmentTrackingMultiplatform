import java.time.Instant
import java.time.temporal.ChronoUnit

class ExpressShipment(id: String, private val createdTimestamp: Long) : Shipment(id) {
    override fun checkErrorsInTimestamp(createdTimestamp: Long) {
        val expected = getExpectedDeliveryDateTimestamp() ?: return
        val createdDate = Instant.ofEpochMilli(createdTimestamp)
        val expectedDate = Instant.ofEpochMilli(expected)
        val daysBetween = ChronoUnit.DAYS.between(createdDate, expectedDate)

        if (daysBetween > 3) {
            addNote("Warning: An express shipment was updated to have a delivery date more than 3 days after creation.")
        }
    }
}