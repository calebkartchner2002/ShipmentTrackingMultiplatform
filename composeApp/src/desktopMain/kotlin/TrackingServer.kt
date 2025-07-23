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
                val updateText = call.receiveParameters()["update"]

                if (updateText.isNullOrBlank()) {
                    call.respondHtml(HttpStatusCode.BadRequest) {
                        body {
                            h3 { +"No update provided." }
                            a(href = "/") { +"Go back" }
                        }
                    }
                    return@post
                }

                println("Received update from form: $updateText")

                try {
                    TrackingSimulator.processLine(updateText)

                    call.respondHtml(HttpStatusCode.OK) {
                        head { title("Shipment Update Sender") }
                        body {
                            h3 { +"Update processed successfully!" }
                            p { +"Sent: $updateText" }
                            br
                            a(href = "/") { +"Submit another update" }
                        }
                    }
                } catch (e: Exception) {
                    call.respondHtml(HttpStatusCode.InternalServerError) {
                        body {
                            h3 { +"Error processing update: ${e.message}" }
                            a(href = "/") { +"Go back and try again" }
                        }
                    }
                }
            }
        }
    }.start(wait = false) // background thread so Compose can launch too
}
