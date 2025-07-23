import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun startTrackingServer() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    head {
                        meta(charset = "UTF-8")
                        title("Shipment Update Sender")
                    }
                    body {
                        h2 { +"Paste Shipment Update" }
                        form(action = "/update", method = FormMethod.post) {
                            textArea {
                                name = "update"
                                rows = "4"
                                cols = "60"
                                placeholder = "e.g., created,12345,standard,1721779200000"
                            }
                            br()
                            submitInput { value = "Send Update" }
                        }
                    }
                }
            }

            post("/update") {
                val line = call.receiveText()
                println("Received update: $line")
                try {
                    TrackingSimulator.processLine(line)
                    call.respondText("Update processed successfully.")
                } catch (e: Exception) {
                    call.respondText("Error: ${e.message}")
                }
            }
        }
    }.start(wait = false) // background thread so Compose can launch too
}
