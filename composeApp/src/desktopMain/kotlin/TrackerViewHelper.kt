import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TrackerViewHelper : ShipmentObserver {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    // UI state
    private val _shipmentId = mutableStateOf<String?>(null)
    val shipmentId: State<String?> = _shipmentId

    private val _shipmentStatus = mutableStateOf("Not Tracking")
    val shipmentStatus: State<String> = _shipmentStatus

    private val _expectedDeliveryDate = mutableStateOf("")
    val expectedDeliveryDate: State<String> = _expectedDeliveryDate

    val shipmentNotes = mutableStateListOf<String>()
    val shipmentUpdateHistory = mutableStateListOf<String>()

    private var currentShipment: Shipment? = null

    fun trackShipment(id: String) {
        val shipment = TrackingSimulator.findShipment(id)

        if (shipment == null) {
            _shipmentStatus.value = "Shipment not found"
            return
        }

        // Clean old state
        stopTracking()

        _shipmentId.value = id
        currentShipment = shipment
        shipment.addObserver(this)

        // Populate initial state
        _shipmentStatus.value = shipment.status
        _expectedDeliveryDate.value = formatTimestamp(shipment.getExpectedDeliveryDateTimestamp())
        shipmentNotes.addAll(shipment.getNotes())
        shipmentUpdateHistory.addAll(formatHistory(shipment.getUpdateHistory()))
    }

    fun stopTracking() {
        currentShipment?.removeObserver(this)
        currentShipment = null

        _shipmentId.value = null
        _shipmentStatus.value = "Not Tracking"
        _expectedDeliveryDate.value = ""
        shipmentNotes.clear()
        shipmentUpdateHistory.clear()
    }

    override fun onShipmentUpdated(shipment: Shipment) {
        coroutineScope.launch {
            // Refresh UI data
            _shipmentStatus.value = shipment.status
            _expectedDeliveryDate.value = formatTimestamp(shipment.getExpectedDeliveryDateTimestamp())

            shipmentNotes.clear()
            shipmentNotes.addAll(shipment.getNotes())

            shipmentUpdateHistory.clear()
            shipmentUpdateHistory.addAll(formatHistory(shipment.getUpdateHistory()))
        }
    }

    private fun formatHistory(updates: List<ShippingUpdate>): List<String> {
        return updates.map {
            val date = formatTimestamp(it.timestamp)
            "Shipment went from ${it.previousStatus} to ${it.newStatus} on $date"
        }
    }

    private fun formatTimestamp(timestamp: Long?): String {
        if (timestamp == null) return "N/A"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
