import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
}

repositories {
    google()
    mavenCentral()
}



group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))


    // Logger
    implementation("ch.qos.logback:logback-classic:1.4.6")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // Driver de SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    //SimpleXml
    implementation("org.simpleframework:simple-xml:2.7.1")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")

    // Result un poco más profesional con tus propios errores de dominio
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    // Mockk
    testImplementation("io.mockk:mockk:1.13.5")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}