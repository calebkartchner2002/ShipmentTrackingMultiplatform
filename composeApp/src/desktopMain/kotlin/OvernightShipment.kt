import java.time.Instant
import java.time.temporal.ChronoUnit

class OvernightShipment(id: String, private val createdTimestamp: Long) : Shipment(id) {
    override fun checkErrorsInTimestamp(createdTimestamp: Long) {
        val expected = getExpectedDeliveryDateTimestamp() ?: return
        val createdDate = Instant.ofEpochMilli(createdTimestamp)
        val expectedDate = Instant.ofEpochMilli(expected)
        val daysBetween = ChronoUnit.DAYS.between(createdDate, expectedDate)

        if (daysBetween != 1L) {
            addNote("Warning: An overnight shipment was updated to have a delivery date not exactly 1 day after creation.")
        }
    }
}