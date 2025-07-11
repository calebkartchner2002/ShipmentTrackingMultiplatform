import kotlin.test.*

class TestLocationUpdateStrategy {

    @Test
    fun testApplyLocationUpdate() {
        val shipment = Shipment("loc1")
        val strategy = LocationUpdateStrategy()
        strategy.apply(shipment, listOf("123456", "Seattle WA"))

        assertEquals("Seattle WA", shipment.getCurrentLocation())
        assertEquals(1, shipment.getUpdateHistory().size)
    }
}
