import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember

fun main() = application {
    startTrackingServer()

    Window(onCloseRequest = ::exitApplication, title = "Shipment Tracker") {
        MaterialTheme {
            val viewHelper = remember { TrackerViewHelper() }
            ShipmentTrackerScreen(viewHelper)
        }
    }
}
