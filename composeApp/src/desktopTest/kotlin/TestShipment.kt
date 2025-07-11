import kotlin.test.*

class TestShipment {

    @Test
    fun testAddNoteAndUpdate() {
        val shipment = Shipment("abc123")
        shipment.addNote("Packed and ready")

        shipment.addUpdate(ShippingUpdate("created", "shipped", 1000L))

        assertEquals("shipped", shipment.status)
        assertEquals(listOf("Packed and ready"), shipment.getNotes())
        assertEquals(1, shipment.getUpdateHistory().size)
    }

    @Test
    fun testObserverNotified() {
        val shipment = Shipment("abc123")
        var notified = false

        val observer = object : ShipmentObserver {
            override fun onShipmentUpdated(shipment: Shipment) {
                notified = true
            }
        }

        shipment.addObserver(observer)
        shipment.addNote("Hello")

        assertTrue(notified)
    }
}
