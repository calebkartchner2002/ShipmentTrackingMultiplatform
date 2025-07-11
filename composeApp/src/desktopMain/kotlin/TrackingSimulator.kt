import kotlinx.coroutines.*
import java.io.File

object TrackingSimulator {
    private val shipments = mutableMapOf<String, Shipment>()
    private var isRunning = false

    fun addShipment(shipment: Shipment) {
        shipments[shipment.id] = shipment
    }

    fun findShipment(id: String): Shipment? {
        return shipments[id]
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun runSimulation(filePath: String) {
        if (isRunning) return
        isRunning = true

        GlobalScope.launch {
            File(filePath).readLines().forEach { line ->
                processLine(line)
                delay(1000)
            }
        }
    }

    private suspend fun processLine(line: String) {
        val tokens = line.split(",")
        if (tokens.size < 3) {
            println("Skipping malformed line: $line")
            return
        }

        val updateType = tokens[0].trim().lowercase()
        val shipmentId = tokens[1].trim()

        val strategy = UpdateStrategyFactory.getStrategy(updateType)
        if (strategy == null) {
            println("Unknown update type: $updateType")
            return
        }

        val shipment = shipments.getOrPut(shipmentId) { Shipment(shipmentId) }
        val info = tokens.drop(2)
        strategy.apply(shipment, info)
    }
}
