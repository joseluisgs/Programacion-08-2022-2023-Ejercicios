plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Results
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    // MyBatis
    implementation("org.mybatis:mybatis:3.5.13")

    // MySQL driver
    //implementation("mysql:mysql-connector-java:8.0.32")

    // SQLite driver
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    // Kotlin logger
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // KOTLIN OPCIÓN 1
    // JUNIT
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    // Mockk para test
    testImplementation("io.mockk:mockk:1.13.3")

    // KOTLIN OPCION 2 (unitarios propia de KOTLIN)
    //testImplementation(kotlin("test"))

    // Simple XML
    implementation("org.simpleframework:simple-xml:2.7.1")

    // GSON
    implementation("com.google.code.gson:gson:2.8.9")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}