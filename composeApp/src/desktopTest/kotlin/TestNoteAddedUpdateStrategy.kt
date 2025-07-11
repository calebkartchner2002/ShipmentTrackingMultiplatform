import kotlin.test.*

class TestNoteAddedUpdateStrategy {

    @Test
    fun testApplyNoteUpdate() {
        val shipment = Shipment("note1")
        val strategy = NoteAddedUpdateStrategy()
        strategy.apply(shipment, listOf("123456", "Label peeled off"))

        assertTrue("Label peeled off" in shipment.getNotes())
        assertEquals(1, shipment.getUpdateHistory().size)
    }
}
