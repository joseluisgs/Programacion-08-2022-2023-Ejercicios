plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Logger
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // Driver de SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    // H2
    implementation("com.h2database:h2:2.1.214")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")

    // Simple XML
    implementation("org.simpleframework:simple-xml:2.7.1")

    // Result un poco más profesional con tus propios errores de dominio
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    // Mockk
    testImplementation("io.mockk:mockk:1.13.5")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()

}

kotlin {
    jvmToolchain(8)
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}