import kotlinx.coroutines.*
import kotlin.test.*

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
    fun testProcessLineSimulatesBatchCorrectly() = runBlocking {
        val lines = listOf(
            "created,s10000,standard,1689700000000",
            "created,s10001,standard,1689700000000",
            "created,s10002,standard,1689700000000",
            "created,s10003,standard,1689700000000",
            "created,s10004,standard,1689700000000",
            "created,s10005,standard,1689700000000",
            "created,s10006,standard,1689700000000",
            "created,s10007,standard,1689700000000",
            "created,s10008,standard,1689700000000",
            "created,s10009,standard,1689700000000",
            "delayed,s10000,1689700200000,1689720000000",
            "lost,s10000,1689700300000",
            "noteadded,s10000,1689700400000,Inspected at warehouse",
            "noteadded,s10000,1689700500000,Inspection passed",
            "canceled,s10003,1689700600000",
            "delivered,s10006,1689700700000"
        )

        // Apply all updates directly
        for (line in lines) {
            TrackingSimulator.processLine(line)
        }

        // Assertions
        val ids = (0..9).map { "s1000$it" }
        for (id in ids) {
            val shipment = TrackingSimulator.findShipment(id)
            assertNotNull(shipment, "Shipment $id should exist")
        }

        val s0 = TrackingSimulator.findShipment("s10000")!!
        val s3 = TrackingSimulator.findShipment("s10003")!!
        val s6 = TrackingSimulator.findShipment("s10006")!!

        assertEquals("lost", s0.status, "s10000 should be marked as lost")
        assertEquals("canceled", s3.status, "s10003 should be marked as canceled")
        assertEquals("delivered", s6.status, "s10006 should be marked as delivered")

        assertEquals(2, s0.getNotes().size, "s10000 should have 2 notes")
        assertTrue(s0.getNotes().last().contains("passed"), "Last note should mention 'passed'")

        assertTrue(s0.getUpdateHistory().size >= 5, "s10000 should have at least 5 updates")
    }
}
