import androidx.compose.runtime.*

class TrackerViewHelper {
    private val trackedShipments = mutableStateMapOf<String, TrackedShipmentViewModel>()
    var errorMessage = mutableStateOf<String?>(null)

    fun trackShipment(id: String) {
        if (trackedShipments.containsKey(id)) return

        val shipment = TrackingSimulator.findShipment(id)
        if (shipment != null) {
            trackedShipments[id] = TrackedShipmentViewModel(shipment)
            errorMessage.value = null
        }
        else {
            errorMessage.value = "Shipment $id not found"
        }
    }

    fun stopTracking(id: String) {
        trackedShipments.remove(id)?.stop()
    }

    fun getTrackedShipments(): Map<String, TrackedShipmentViewModel> = trackedShipments
}
