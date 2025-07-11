import kotlin.test.*
import kotlinx.coroutines.*

class TestTrackerViewHelper {

    private lateinit var helper: TrackerViewHelper
    private lateinit var shipment: Shipment

    @BeforeTest
    fun setup() {
        TrackingSimulator.reset()
        shipment = Shipment("test123")
        shipment.addNote("Initial packaging complete")
        shipment.addUpdate(ShippingUpdate("created", "shipped", 123456789))

        TrackingSimulator.addShipment(shipment)
        helper = TrackerViewHelper()
    }

    @Test
    fun testTrackShipmentInitialStateLoads() = runBlocking {
        helper.trackShipment("test123")

        // Allow time for state propagation
        delay(100)

        assertEquals("test123", helper.shipmentId.value)
        assertEquals("shipped", helper.shipmentStatus.value)
        assertEquals(1, helper.shipmentNotes.size)
        assertEquals("Initial packaging complete", helper.shipmentNotes[0])
        assertTrue(helper.shipmentUpdateHistory.any { it.contains("shipped") })
    }

    @Test
    fun testObserverUpdatesReflectInState() = runBlocking {
        helper.trackShipment("test123")

        shipment.addNote("Second note added")
        shipment.setCurrentLocation("Chicago IL")
        shipment.addUpdate(ShippingUpdate("shipped", "delivered", 987654321))

        delay(100)

        assertEquals("delivered", helper.shipmentStatus.value)
        assertEquals("Chicago IL", shipment.getCurrentLocation())
        assertTrue(helper.shipmentNotes.contains("Second note added"))
        assertTrue(helper.shipmentUpdateHistory.last().contains("delivered"))
    }

    @Test
    fun testStopTrackingClearsState() = runBlocking {
        helper.trackShipment("test123")
        delay(100)

        helper.stopTracking()
        delay(50)

        assertNull(helper.shipmentId.value)
        assertEquals("Not Tracking", helper.shipmentStatus.value)
        assertTrue(helper.shipmentNotes.isEmpty())
        assertTrue(helper.shipmentUpdateHistory.isEmpty())
    }
}
