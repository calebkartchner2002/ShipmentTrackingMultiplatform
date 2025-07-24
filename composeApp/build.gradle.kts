import org.jetbrains.compose.desktop.application.dsl.TargetFormat

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlinx" && requested.name.startsWith("kotlinx-coroutines")) {
            useVersion("1.7.3")
            because("Ktor 2.3.4 requires kotlinx.coroutines 1.7.3+")
        }
    }
}


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")

            implementation("io.ktor:ktor-server-core:2.3.4")
            implementation("io.ktor:ktor-server-netty:2.3.4")
            implementation("io.ktor:ktor-server-content-negotiation:2.3.4")
            implementation("io.ktor:ktor-server-html-builder:2.3.4")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
