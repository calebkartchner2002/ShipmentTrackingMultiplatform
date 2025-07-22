import java.time.Instant
import java.time.temporal.ChronoUnit

class BulkShipment(id: String, private val createdTimestamp: Long) : Shipment(id) {
    override fun checkErrorsInTimestamp(createdTimestamp: Long) {
        val expected = getExpectedDeliveryDateTimestamp() ?: return
        val createdDate = Instant.ofEpochMilli(createdTimestamp)
        val expectedDate = Instant.ofEpochMilli(expected)
        val daysBetween = ChronoUnit.DAYS.between(createdDate, expectedDate)

        if (daysBetween < 3) {
            addNote("Warning: A bulk shipment was updated to have a delivery date sooner than 3 days after creation.")
        }
    }
}