import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


fun main() = application {
    // Load the file using the class loader
    val resourceUrl = object {}.javaClass.classLoader.getResource("test.txt")
    requireNotNull(resourceUrl) { "test.txt not found in resources!" }

    val filePath = resourceUrl.path
    println("Running simulation using: $filePath")

    TrackingSimulator.runSimulation(filePath, delayMillis = 1000L)

    Window(onCloseRequest = ::exitApplication, title = "Shipment Tracker") {
        MaterialTheme {
            val viewHelper = remember { TrackerViewHelper() }
            ShipmentTrackerScreen(viewHelper)
        }
    }
}

