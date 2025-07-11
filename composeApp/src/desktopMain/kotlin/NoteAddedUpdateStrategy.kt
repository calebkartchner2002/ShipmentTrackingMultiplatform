class NoteAddedUpdateStrategy : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val note = info[1]
        shipment.addNote(note)
        val update = ShippingUpdate(shipment.status, shipment.status, timestamp)
        shipment.addUpdate(update)
    }
}
