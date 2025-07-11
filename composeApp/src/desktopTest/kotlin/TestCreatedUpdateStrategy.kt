import kotlin.test.*

class TestCreatedUpdateStrategy {

    @Test
    fun testApplyCreatedUpdate() {
        val shipment = Shipment("test")
        val strategy = CreatedUpdateStrategy()
        strategy.apply(shipment, listOf("123456789"))

        val updates = shipment.getUpdateHistory()
        assertEquals("created", shipment.status)
        assertEquals(1, updates.size)
        assertEquals("created", updates[0].newStatus)
    }
}
