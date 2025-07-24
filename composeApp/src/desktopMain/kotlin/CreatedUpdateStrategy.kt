class CreatedUpdateStrategy : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val update = ShippingUpdate("none", "created", timestamp)
        shipment.setCreatedTimestamp(timestamp)
        shipment.addUpdate(update)
    }
}
