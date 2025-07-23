object TrackingSimulator {
    private val shipments = mutableMapOf<String, Shipment>()

    fun addShipment(shipment: Shipment) {
        shipments[shipment.id] = shipment
    }

    fun findShipment(id: String): Shipment? {
        return shipments[id]
    }

    fun processLine(line: String) {
        println("Processing line: $line")
        val tokens = line.split(",").toMutableList()
        if (tokens.size < 3) {
            println("Skipping malformed line: $line")
            return
        }

        val updateType = tokens[0].trim().lowercase()
        val shipmentId = tokens[1].trim()

        val strategy = UpdateStrategyFactory.getStrategy(updateType)
        if (strategy == null) {
            println("Unknown update type: $updateType")
            return
        }

        if (updateType == "created") {
            if (tokens.size < 4) {
                println("Malformed 'created' line: $line")
                return
            }

            val shipmentType = tokens[2].trim()
            val creationTimestamp = tokens[3].trim().toLongOrNull()
            if (creationTimestamp == null) {
                println("Invalid timestamp: $line")
                return
            }

            val shipment = ShipmentFactory.createShipment(shipmentId, shipmentType, creationTimestamp)
            addShipment(shipment)

            // Remove type and timestamp for remaining info list
            tokens.removeAt(2)
        }

        val shipment = shipments[shipmentId]
        if (shipment == null) {
            println("Shipment not found: $shipmentId")
            return
        }

        val info = tokens.drop(2)
        strategy.apply(shipment, info)
    }

    fun reset() {
        shipments.clear()
    }
}
