plugins {
    kotlin("jvm") version "1.5.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.compileClasspath.get().files.map { if (it.isDirectory) it else zipTree(it) })
    from(sourceSets.main.get().output)
}