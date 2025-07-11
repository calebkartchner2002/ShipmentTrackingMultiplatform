import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ShipmentCard(vm: TrackedShipmentViewModel, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Shipment ID: ${vm.shipmentId}", style = MaterialTheme.typography.titleMedium)
                Button(onClick = onRemove) {
                    Text("Stop")
                }
            }

            Text("Status: ${vm.shipmentStatus.value}")
            Text("ETA: ${vm.expectedDeliveryDate.value}")

            Spacer(Modifier.height(8.dp))
            Text("Notes:")
            vm.shipmentNotes.forEach { Text("- $it") }

            Spacer(Modifier.height(8.dp))
            Text("History:")
            vm.shipmentUpdateHistory.forEach { Text("- $it") }
        }
    }
}
