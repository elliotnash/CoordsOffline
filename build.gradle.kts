val kotlinVersion: String by project
val mcVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.gradleup.shadow") version "8.3.3"
    id("net.kyori.blossom") version "2.1.0"
}

group = "org.elliotnash.coordsoffline"
version = "2.0.1"

// Replace variables in resource-templates dir
sourceSets {
    main {
        blossom {
            resources {
                property("version", project.version.toString())
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    compileOnly("org.spigotmc:spigot-api:$mcVersion+")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}