class ShippedUpdateStrategy : UpdateStrategy {
    override fun apply(shipment: Shipment, info: List<String>) {
        val timestamp = info[0].toLong()
        val expectedDelivery = info[1].toLong()
        shipment.setExpectedDeliveryDate(expectedDelivery)
        val update = ShippingUpdate(shipment.status, "shipped", timestamp)
        shipment.addUpdate(update)
    }
}
