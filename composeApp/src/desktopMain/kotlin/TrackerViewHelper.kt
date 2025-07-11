import androidx.compose.runtime.*

class TrackerViewHelper {
    private val trackedShipments = mutableStateMapOf<String, TrackedShipmentViewModel>()

    fun trackShipment(id: String) {
        if (trackedShipments.containsKey(id)) return

        val shipment = TrackingSimulator.findShipment(id)
        if (shipment != null) {
            trackedShipments[id] = TrackedShipmentViewModel(shipment)
        }
    }

    fun stopTracking(id: String) {
        trackedShipments.remove(id)?.stop()
    }

    fun getTrackedShipments(): Map<String, TrackedShipmentViewModel> = trackedShipments
}
