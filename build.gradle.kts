import java.text.SimpleDateFormat
import java.util.Date

plugins {
    kotlin("jvm") version "2.0.0"
    id("java-library")
    id("eclipse")
    id("idea")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.27-beta"
}

group = "net.valhelsia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

neoForge {
    version = project.property("neo_version") as String
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Jar> {
    manifest {
        val now = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        attributes(mapOf(
            "Implementation-Timestamp" to now,
            "FMLModType" to "GAMELIBRARY"
        ))
    }
}