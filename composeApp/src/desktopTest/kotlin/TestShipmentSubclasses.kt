import kotlin.test.*
import java.time.Instant

class TestShipmentSubclasses {

    private val oneDayMillis = 24 * 60 * 60 * 1000L

    @BeforeTest
    fun setup() {
        TrackingSimulator.reset()
    }

    @AfterTest
    fun teardown() {
        TrackingSimulator.reset()
    }

    @Test
    fun testExpressShipment() {
        val createdTime = Instant.now().toEpochMilli()
        TrackingSimulator.processLine("created,EXP456,express,$createdTime")
        TrackingSimulator.processLine("expected,EXP456,${createdTime + 2 * oneDayMillis}")

        val shipment = TrackingSimulator.findShipment("EXP456")!!
        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testOvernightShipment() {
        val createdTime = Instant.now().toEpochMilli()
        TrackingSimulator.processLine("created,OVN456,overnight,$createdTime")
        TrackingSimulator.processLine("expected,OVN456,${createdTime + oneDayMillis}")

        val shipment = TrackingSimulator.findShipment("OVN456")!!
        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testBulkShipment() {
        val createdTime = Instant.now().toEpochMilli()
        TrackingSimulator.processLine("created,BLK456,bulk,$createdTime")
        TrackingSimulator.processLine("expected,BLK456,${createdTime + 4 * oneDayMillis}")

        val shipment = TrackingSimulator.findShipment("BLK456")!!
        assertTrue(shipment.getNotes().isEmpty())
    }

    @Test
    fun testStandardShipment() {
        val createdTime = Instant.now().toEpochMilli()
        TrackingSimulator.processLine("created,STD123,standard,$createdTime")
        TrackingSimulator.processLine("expected,STD123,${createdTime + 100 * oneDayMillis}")

        val shipment = TrackingSimulator.findShipment("STD123")!!
        assertTrue(shipment.getNotes().isEmpty())
    }
}
