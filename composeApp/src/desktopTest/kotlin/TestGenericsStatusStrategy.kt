import kotlin.test.*

class TestGenericStatusStrategy {

    @Test
    fun testApplyLostStatusUpdate() {
        val shipment = Shipment("gen-lost")
        val strategy = GenericStatusStrategy("lost")
        strategy.apply(shipment, listOf("111111"))

        assertEquals("lost", shipment.status)
        assertEquals(1, shipment.getUpdateHistory().size)
        assertEquals("lost", shipment.getUpdateHistory()[0].newStatus)
    }

    @Test
    fun testApplyCanceledStatusUpdate() {
        val shipment = Shipment("gen-canceled")
        val strategy = GenericStatusStrategy("canceled")
        strategy.apply(shipment, listOf("222222"))

        assertEquals("canceled", shipment.status)
        assertEquals(1, shipment.getUpdateHistory().size)
        assertEquals("canceled", shipment.getUpdateHistory()[0].newStatus)
    }

    @Test
    fun testApplyDeliveredStatusUpdate() {
        val shipment = Shipment("gen-delivered")
        val strategy = GenericStatusStrategy("delivered")
        strategy.apply(shipment, listOf("333333"))

        assertEquals("delivered", shipment.status)
        assertEquals(1, shipment.getUpdateHistory().size)
        assertEquals("delivered", shipment.getUpdateHistory()[0].newStatus)
    }
}
