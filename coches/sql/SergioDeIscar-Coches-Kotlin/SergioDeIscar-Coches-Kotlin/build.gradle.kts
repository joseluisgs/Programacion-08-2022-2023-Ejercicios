plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    implementation("com.squareup.moshi:moshi-adapters:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    implementation("org.simpleframework:simple-xml:2.7.1")

    implementation("com.h2database:h2:2.1.210")

    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    implementation("org.mybatis:mybatis:3.5.11")

    // MockK
    testImplementation("io.mockk:mockk:1.13.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}