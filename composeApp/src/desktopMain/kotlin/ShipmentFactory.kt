object ShipmentFactory {
    fun createShipment(id: String, typeString: String, creationTimestamp: Long): Shipment {
        return when (typeString.lowercase()) {
            "express" -> ExpressShipment(id, creationTimestamp)
            "overnight" -> OvernightShipment(id, creationTimestamp)
            "bulk" -> BulkShipment(id, creationTimestamp)
            else -> StandardShipment(id, creationTimestamp)
        }
    }
}

