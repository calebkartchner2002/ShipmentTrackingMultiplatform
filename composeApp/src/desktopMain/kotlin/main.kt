import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun main() = application {
    GlobalScope.launch {
        TrackingSimulator.runSimulation("src/main/resources/shipment_updates.txt")
    }

    Window(onCloseRequest = ::exitApplication, title = "Shipment Tracker") {
        MaterialTheme {
            val viewHelper = remember { TrackerViewHelper() }
            ShipmentTrackerScreen(viewHelper)
        }
    }
}
