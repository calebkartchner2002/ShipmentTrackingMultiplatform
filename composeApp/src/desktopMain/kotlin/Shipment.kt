open class Shipment(
    val id: String
) {
    var status: String = "created"
        private set

    private val notes: MutableList<String> = mutableListOf()
    private val updateHistory: MutableList<ShippingUpdate> = mutableListOf()
    private var expectedDeliveryDateTimestamp: Long? = null
    private var currentLocation: String = ""
    private var createdTimestamp: Long? = null

    private val observers = mutableListOf<ShipmentObserver>()

    fun addNote(note: String) {
        notes.add(note)
        notifyObservers()
    }

    fun addUpdate(update: ShippingUpdate) {
        updateHistory.add(update)
        status = update.newStatus
        notifyObservers()
    }

    fun setExpectedDeliveryDate(timestamp: Long) {
        expectedDeliveryDateTimestamp = timestamp
        checkErrorsInTimestamp()
        notifyObservers()
    }

    fun setCurrentLocation(location: String) {
        currentLocation = location
        notifyObservers()
    }

    // Observer pattern
    fun addObserver(observer: ShipmentObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: ShipmentObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers() {
        observers.forEach { it.onShipmentUpdated(this) }
    }
    open fun checkErrorsInTimestamp(){
        null
    }

    fun setCreatedTimestamp(timestamp: Long) {
        if (createdTimestamp == null) {
            createdTimestamp = timestamp
        }
    }

    fun getCreatedTimestamp(): Long? = createdTimestamp
    fun getNotes(): List<String> = notes.toList()
    fun getUpdateHistory(): List<ShippingUpdate> = updateHistory.toList()
    fun getExpectedDeliveryDateTimestamp(): Long? = expectedDeliveryDateTimestamp
    fun getCurrentLocation(): String = currentLocation
}