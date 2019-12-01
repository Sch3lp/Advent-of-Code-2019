buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    kotlin("jvm") version "1.3.21"
}

subprojects {

    apply { plugin("kotlin") }

    repositories {
        mavenCentral()
    }

    val test by tasks.getting(Test::class) {
        useJUnitPlatform { }
    }

    val junit5Version = "5.4.2"

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.21")
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.21")

        testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")
        testImplementation("org.assertj:assertj-core:3.14.0")
    }
}


tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
