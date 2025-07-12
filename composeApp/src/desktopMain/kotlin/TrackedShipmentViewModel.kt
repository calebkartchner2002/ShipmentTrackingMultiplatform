import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.*

class TrackedShipmentViewModel(val shipment: Shipment) : ShipmentObserver {

    val shipmentId = shipment.id
    val shipmentStatus = mutableStateOf(shipment.status)
    val expectedDeliveryDate = mutableStateOf(formatTimestamp(shipment.getExpectedDeliveryDateTimestamp()))
    val shipmentNotes = mutableStateListOf<String>()
    val shipmentUpdateHistory = mutableStateListOf<String>()
    val location: State<String> = derivedStateOf { shipment.getCurrentLocation() }


    init {
        shipment.addObserver(this)
        shipmentNotes.addAll(shipment.getNotes())
        shipmentUpdateHistory.addAll(formatHistory(shipment.getUpdateHistory()))
    }

    override fun onShipmentUpdated(shipment: Shipment) {
        shipmentStatus.value = shipment.status
        expectedDeliveryDate.value = formatTimestamp(shipment.getExpectedDeliveryDateTimestamp())

        shipmentNotes.clear()
        shipmentNotes.addAll(shipment.getNotes())

        shipmentUpdateHistory.clear()
        shipmentUpdateHistory.addAll(formatHistory(shipment.getUpdateHistory()))
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

    fun stop() {
        shipment.removeObserver(this)
    }
}
