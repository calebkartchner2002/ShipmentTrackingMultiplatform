import kotlin.test.*
class TestShippedUpdateStrategy {

    @Test
    fun testApplyShippedUpdate() {
        val shipment = Shipment("ship1")
        val strategy = ShippedUpdateStrategy()
        strategy.apply(shipment, listOf("123000", "124000"))

        assertEquals("shipped", shipment.status)
        assertEquals(124000, shipment.getExpectedDeliveryDateTimestamp())
    }
}
