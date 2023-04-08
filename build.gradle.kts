import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

/*group = "softdevSpringTask"*/
version = "1.0"

repositories {
    mavenCentral()
}

application {
    // Define the main class for the application.
    mainClass.set("org.example.softdev2023springtask2.MainKt")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("info.picocli:picocli:4.7.1")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.softdev2023springtask2.MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}