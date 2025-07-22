import kotlin.test.Test
import java.io.File
import kotlinx.coroutines.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestTrackingSimulator {

    @Test
    fun testRunSimulationCreatesShipment() {
        runBlocking {
            val tempFile = File.createTempFile("shipment_test", ".txt")
            tempFile.writeText(
                """
                created,98765,standard,1689700000000
                shipped,98765,1689701000000,1689750000000
                """.trimIndent()
            )

            TrackingSimulator.runSimulation(tempFile.absolutePath)

            delay(2500) // wait 2.5s for coroutine to process two updates

            val shipment = TrackingSimulator.findShipment("98765")
            assertNotNull(shipment)
            assertEquals("shipped", shipment.status)
            assertEquals(2, shipment.getUpdateHistory().size)
            assertTrue(shipment.getNotes().isEmpty(), "Expected no warning notes for standard shipment")


            tempFile.delete()
        }
    }
}
