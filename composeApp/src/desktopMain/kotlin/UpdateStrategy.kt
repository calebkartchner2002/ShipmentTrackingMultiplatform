interface UpdateStrategy {
    fun apply(shipment: Shipment, info: List<String>)
}
