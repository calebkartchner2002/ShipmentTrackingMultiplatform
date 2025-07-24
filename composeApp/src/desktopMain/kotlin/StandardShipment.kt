class StandardShipment(id: String) : Shipment(id) {
    override fun checkErrorsInTimestamp() {
        // No restrictions
    }
}
