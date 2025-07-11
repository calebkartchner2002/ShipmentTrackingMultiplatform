import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShipmentTrackerScreen(viewHelper: TrackerViewHelper) {
    var inputId by remember { mutableStateOf("") }
    val shipmentId by viewHelper.shipmentId
    val status by viewHelper.shipmentStatus
    val eta by viewHelper.expectedDeliveryDate
    val notes = viewHelper.shipmentNotes
    val updates = viewHelper.shipmentUpdateHistory

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Shipment Tracking", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("Enter Shipment ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = { viewHelper.trackShipment(inputId) }) {
                Text("Track")
            }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = { viewHelper.stopTracking() }) {
                Text("Stop Tracking")
            }
        }

        Spacer(Modifier.height(24.dp))
        Divider()

        Text("Tracking: ${shipmentId ?: "None"}", style = MaterialTheme.typography.titleMedium)
        Text("Status: $status")
        Text("ETA: $eta")

        Spacer(Modifier.height(16.dp))

        if (notes.isNotEmpty()) {
            Text("Notes:", style = MaterialTheme.typography.titleSmall)
            LazyColumn {
                items(notes) { note ->
                    Text("- $note", modifier = Modifier.padding(4.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (updates.isNotEmpty()) {
            Text("Update History:", style = MaterialTheme.typography.titleSmall)
            LazyColumn {
                items(updates) { update ->
                    Text("- $update", modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}
