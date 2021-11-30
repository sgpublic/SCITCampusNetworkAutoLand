import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
}

group = "io.github.sgpublic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")

    // 模拟浏览器请求
    implementation("net.sourceforge.htmlunit:htmlunit:2.55.0")

    // ini 解析
    implementation("org.ini4j:ini4j:0.5.4")

    // logback
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha10")
    implementation("ch.qos.logback:logback-core:1.3.0-alpha10")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("io.github.sgpublic.campus.Application")
}