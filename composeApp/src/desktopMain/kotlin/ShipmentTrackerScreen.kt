import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

@Composable
fun ShipmentTrackerScreen(viewHelper: TrackerViewHelper) {
    var inputId by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Track a Shipment", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("Shipment ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewHelper.trackShipment(inputId)
                inputId = ""
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Track")
        }
        viewHelper.errorMessage.value?.let {
            Text(it, color = Color.Red)
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        val tracked = viewHelper.getTrackedShipments().values.toList()

        LazyColumn(
            modifier = Modifier.fillMaxHeight().padding(bottom = 16.dp)
        ) {
            items(tracked, key = { it.shipmentId }) { vm ->
                ShipmentCard(vm = vm, onRemove = { viewHelper.stopTracking(vm.shipmentId) })
            }
        }
    }
}
