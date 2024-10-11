import java.text.SimpleDateFormat
import java.util.Date

plugins {
    kotlin("jvm") version "2.0.0"
    id("java-library")
    id("eclipse")
    id("idea")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.36-beta"
}

group = "net.valhelsia"
version = "0.1.8"

repositories {
    mavenCentral()
}

neoForge {
    version = project.property("neo_version") as String

    interfaceInjectionData {
        from("interfaces.json")
        publish(file("interfaces.json"))
    }
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
            "MixinConfigs" to "dataforge.mixins.json",
            "FMLModType" to "GAMELIBRARY"
        ))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "dataforge"
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ValhelsiaTeam/DataForge")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}