object ShipmentFactory {
    fun createShipment(id: String, typeString: String, creationTimestamp: Long): Shipment {
        return when (typeString.lowercase()) {
            "express" -> ExpressShipment(id)
            "overnight" -> OvernightShipment(id)
            "bulk" -> BulkShipment(id)
            else -> StandardShipment(id)
        }
    }
}

