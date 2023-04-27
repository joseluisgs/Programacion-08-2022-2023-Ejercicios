import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("app.cash.sqldelight") version "2.0.0-alpha05"
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
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")
    testImplementation("io.mockk:mockk:1.13.5")
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("dev.ivanrc.database")
        }
    }
}