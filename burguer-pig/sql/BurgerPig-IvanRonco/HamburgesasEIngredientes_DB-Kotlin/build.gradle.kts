import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.5")

    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    implementation("com.h2database:h2:2.1.214")

    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("org.simpleframework:simple-xml:2.7.1")

    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}