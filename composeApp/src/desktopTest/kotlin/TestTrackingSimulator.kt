import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestTrackingSimulator {

    @Test
    fun testProcessLineCreatesAndUpdatesShipment() = runBlocking {
        // Reset simulator state before test
        TrackingSimulator.reset()

        // Simulate the "created" line
        TrackingSimulator.processLine("created,98765,standard,1689700000000")

        // Simulate the "shipped" line
        TrackingSimulator.processLine("shipped,98765,1689701000000,1689750000000")

        // Validate
        val shipment = TrackingSimulator.findShipment("98765")
        assertNotNull(shipment)
        assertEquals("shipped", shipment.status)
        assertEquals(2, shipment.getUpdateHistory().size)
        assertTrue(shipment.getNotes().isEmpty(), "Expected no warning notes for standard shipment")
    }
}
