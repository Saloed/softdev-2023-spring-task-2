plugins {
    java
    application
}

group = "find"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("task2.Parser")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "task2.Parser"
        attributes["Parse-Class"] = "Parser"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("args4j:args4j:2.32")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


