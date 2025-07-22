import kotlin.test.*
import java.time.Instant

class TestShipmentSubclasses {

    private val oneDayMillis = 24 * 60 * 60 * 1000L

    @Test
    fun testExpressShipmentViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = ExpressShipment("EXP123", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + 4 * oneDayMillis) // too far out
        shipment.checkErrorsInTimestamp(createdTime)

        val notes = shipment.getNotes()
        assertEquals(1, notes.size)
        assertTrue(notes[0].contains("express shipment") && notes[0].contains("more than 3 days"))
    }

    @Test
    fun testExpressShipmentNoViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = ExpressShipment("EXP456", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + 2 * oneDayMillis) // valid
        shipment.checkErrorsInTimestamp(createdTime)

        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testOvernightShipmentViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = OvernightShipment("OVN123", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + 2 * oneDayMillis) // too late
        shipment.checkErrorsInTimestamp(createdTime)

        val notes = shipment.getNotes()
        assertEquals(1, notes.size)
        assertTrue(notes[0].contains("overnight shipment") && notes[0].contains("not exactly 1 day"))
    }

    @Test
    fun testOvernightShipmentNoViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = OvernightShipment("OVN456", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + oneDayMillis) // exactly 1 day
        shipment.checkErrorsInTimestamp(createdTime)

        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testBulkShipmentViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = BulkShipment("BLK123", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + oneDayMillis) // too soon
        shipment.checkErrorsInTimestamp(createdTime)

        val notes = shipment.getNotes()
        assertEquals(1, notes.size)
        assertTrue(notes[0].contains("bulk shipment") && notes[0].contains("sooner than 3 days"))
    }

    @Test
    fun testBulkShipmentNoViolation() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = BulkShipment("BLK456", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + 4 * oneDayMillis) // valid
        shipment.checkErrorsInTimestamp(createdTime)

        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testStandardShipmentNeverViolates() {
        val createdTime = Instant.now().toEpochMilli()
        val shipment = StandardShipment("STD123", createdTime)

        shipment.setExpectedDeliveryDate(createdTime + 100 * oneDayMillis) // completely arbitrary
        shipment.checkErrorsInTimestamp(createdTime)

        assertTrue(shipment.getNotes().isEmpty())
    }
}
