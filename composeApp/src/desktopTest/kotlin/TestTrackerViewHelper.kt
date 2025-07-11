import kotlin.test.*
import kotlinx.coroutines.*
import java.io.File

class TestTrackerViewHelper {

    private lateinit var viewHelper: TrackerViewHelper

    @BeforeTest
    fun setup() {
        viewHelper = TrackerViewHelper()
    TrackingSimulator.reset()
    }

    @Test
    fun testTrackingSingleShipment() = runBlocking {
        // Create a sample shipment and add it to simulator
        val shipment = Shipment("abc123")
        TrackingSimulator.addShipment(shipment)

        // Start tracking
        viewHelper.trackShipment("abc123")
        val trackedMap = viewHelper.getTrackedShipments()
        val vm = trackedMap["abc123"]

        assertNotNull(vm, "Shipment view model should be tracked")
        assertEquals("abc123", vm.shipmentId)
    }

    @Test
    fun testStopTrackingShipment() = runBlocking {
        val shipment = Shipment("xyz789")
        TrackingSimulator.addShipment(shipment)

        viewHelper.trackShipment("xyz789")
        assertTrue("xyz789" in viewHelper.getTrackedShipments().keys)

        viewHelper.stopTracking("xyz789")
        assertFalse("xyz789" in viewHelper.getTrackedShipments().keys)
    }

    @Test
    fun testShipmentUpdatesPropagateToViewModel() = runBlocking {
        val shipment = Shipment("s3000")
        TrackingSimulator.addShipment(shipment)

        viewHelper.trackShipment("s3000")
        val vm = viewHelper.getTrackedShipments()["s3000"]
        assertNotNull(vm)

        // Add update to shipment
        val update = ShippingUpdate("created", "shipped", 1652719999999)
        shipment.addUpdate(update)

        // Let observer propagate
        delay(50)

        // Check update in ViewModel
        val history = vm.shipmentUpdateHistory
        assertTrue(history.any { it.contains("shipped") }, "Update history should reflect the status change")
    }
}
