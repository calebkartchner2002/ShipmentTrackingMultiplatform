class LocationUpdateStrategy : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val location = info[1]
        shipment.setCurrentLocation(location)
        val update = ShippingUpdate(shipment.status, shipment.status, timestamp)
        shipment.addUpdate(update)
    }
}
