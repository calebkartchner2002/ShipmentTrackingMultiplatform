class GenericStatusStrategy(private val newStatus: String) : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val update = ShippingUpdate(shipment.status, newStatus, timestamp)
        shipment.addUpdate(update)
    }
}
