import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    kotlin("jvm") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
    application
}

group = "Wololo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("ch.qos.logback:logback-classic:1.4.6")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    //result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")
    // Simple XML
    implementation("org.simpleframework:simple-xml:2.7.1")
    // MyBatis para scripts SQL y otras utilidades
    implementation("org.mybatis:mybatis:3.5.13")
    // Driver de SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")

}

sqldelight {
    databases {
        create("database") {
            packageName.set("database")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}