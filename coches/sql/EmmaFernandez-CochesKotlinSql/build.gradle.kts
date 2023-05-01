plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // MockK
    testImplementation("io.mockk:mockk:1.13.5")

    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi-adapters:1.14.0")

    // Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
