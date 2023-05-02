import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    //SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")
    //Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")
    //XML
    implementation("org.simpleframework:simple-xml:2.7.1")
    //Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")
    //Logger
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    implementation("org.mybatis:mybatis:3.5.11")
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