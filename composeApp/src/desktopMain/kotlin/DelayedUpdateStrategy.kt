class DelayedUpdateStrategy : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val newExpectedDelivery = info[1].toLong()
        shipment.setExpectedDeliveryDate(newExpectedDelivery)
        val update = ShippingUpdate(shipment.status, "delayed", timestamp)
        shipment.addUpdate(update)
    }
}
