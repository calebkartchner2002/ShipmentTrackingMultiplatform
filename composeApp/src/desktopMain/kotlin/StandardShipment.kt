class StandardShipment(id: String, private val createdTimestamp: Long) : Shipment(id) {
    override fun checkErrorsInTimestamp(createdTimestamp: Long) {
    }
}