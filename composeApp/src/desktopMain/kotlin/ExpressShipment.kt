class ExpressShipment(id: String) : Shipment(id) {
    override fun checkErrorsInTimestamp() {
        val created = getCreatedTimestamp()
        val expected = getExpectedDeliveryDateTimestamp()
        if (created != null && expected != null) {
            val diff = expected - created
            if (diff > 3 * 24 * 60 * 60 * 1000L) {
                addNote("Violation: Express shipment delivery is more than 3 days after creation.")
            }
        }
    }
}
