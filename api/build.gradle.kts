val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val h2_version: String by project
val dotenv_version: String by project
val ktor_version: String by project
val supabase_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    //kotlin("plugin.serialization") version "2.1.0"
}

group = "com.adam_and_jan"
version = "0.0.1"

application {
    mainClass.set("com.adam_and_jan.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenv_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-server-cors:$kotlin_version")

    //JBCrypt
    implementation("org.mindrot:jbcrypt:0.4")

    //JWT
    implementation("io.ktor:ktor-server-auth:3.0.3")
    implementation("io.ktor:ktor-server-auth-jwt:3.0.3")

    //Supabase
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.0.3")
    implementation("io.ktor:ktor-client-cio:$ktor_version")


//    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.3"))
//    implementation("io.github.jan-tennert.supabase:postgrest-kt")
//    implementation("io.github.jan-tennert.supabase:realtime-kt")
//
//    implementation("io.ktor:ktor-client-java:$ktor_version")
}
