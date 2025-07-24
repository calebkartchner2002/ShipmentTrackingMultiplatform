class OvernightShipment(id: String) : Shipment(id) {
    override fun checkErrorsInTimestamp() {
        val created = getCreatedTimestamp()
        val expected = getExpectedDeliveryDateTimestamp()
        if (created != null && expected != null) {
            val diff = expected - created
            val oneDay = 24 * 60 * 60 * 1000L
            if (diff != oneDay) {
                addNote("Violation: Overnight shipment delivery is not exactly 1 day after creation.")
            }
        }
    }
}
