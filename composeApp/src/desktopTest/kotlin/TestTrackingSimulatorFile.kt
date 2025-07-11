import kotlinx.coroutines.*
import kotlin.test.*
import java.io.File

class TestTrackingSimulatorFile {

    @BeforeTest
    fun setup() {
        TrackingSimulator.reset()
    }

    @AfterTest
    fun teardown() {
        TrackingSimulator.reset()
    }

    @Test
    fun testSimulationProcessesFileUpdatesCorrectly() = runBlocking {
        val fileUrl = javaClass.classLoader.getResource("test.txt")
        requireNotNull(fileUrl) { "test.txt not found in resources!" }

        val filePath = fileUrl.path
        println("Resolved path: $filePath")

        // Run simulation with 10ms delay per update (for test speed)
        TrackingSimulator.runSimulation(filePath, delayMillis = 10L)

        // Wait long enough for all updates (~90 lines × 10ms = 900ms)
        delay(2000)

        // Check that all 10 shipments exist
        for (i in 0..9) {
            val id = "s1000$i"
            val shipment = TrackingSimulator.findShipment(id)
            assertNotNull(shipment, "Shipment $id should exist")
        }

        // Specific shipment assertions
        val s0 = TrackingSimulator.findShipment("s10000")!!
        val s3 = TrackingSimulator.findShipment("s10003")!!
        val s6 = TrackingSimulator.findShipment("s10006")!!

        assertEquals("lost", s0.status, "s10000 should be marked as lost")
        assertEquals("canceled", s3.status, "s10003 should be marked as canceled")
        assertEquals("delivered", s6.status, "s10006 should be marked as delivered")

        // Check notes on a sample shipment
        assertEquals(3, s0.getNotes().size, "s10000 should have 3 notes")
        assertTrue(s0.getNotes().last().contains("inspection"), "Last note should mention inspection")

        // Check update history length is reasonable
        assertTrue(s0.getUpdateHistory().size >= 5, "s10000 should have at least 5 updates")
    }
}
