val kotlinVersion: String by project
val mcVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.gradleup.shadow") version "8.3.3"
}

group = "org.elliotnash.coordsoffline"
version = "2.0.0"

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