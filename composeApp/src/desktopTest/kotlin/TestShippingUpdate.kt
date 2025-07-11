import kotlin.test.Test
import kotlin.test.assertEquals

class ShippingUpdateTest {

    @Test
    fun testShippingUpdateFields() {
        val update = ShippingUpdate(
            previousStatus = "shipped",
            newStatus = "delivered",
            timestamp = 1699999999999
        )

        assertEquals("shipped", update.previousStatus)
        assertEquals("delivered", update.newStatus)
        assertEquals(1699999999999, update.timestamp)
    }
}
