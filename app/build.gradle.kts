/*
 * The build configuration of HelixNavigator. This includes options to build
 * excecutables with launch4j and jpackage.
 */

plugins {
    `application`
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("edu.sc.seis.launch4j") version "2.5.1"
}

version = "0.0.2"

val packageName = "${name}-${version}"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://maven.pkg.github.com/SleipnirGroup/TrajoptLib")
        credentials {
            username = "jlbabilino"
            password = "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"
        }
    }
}

dependencies {
    implementation("com.github.jlbabilino:json:master-SNAPSHOT")
    // implementation "javax.measure:unit-api:2.1.3"
    // implementation "tec.units:unit-ri:1.0.3"
    // implementation "tech.units:indriya:2.1.3"
    implementation("systems.uom:systems-unicode:2.1")
    implementation("systems.uom:systems-common:2.1")
    implementation("systems.uom:systems-quantity:2.1")
    // implementation "org.djunits:djunits:4.01.07"

    implementation("org.team2363:helixtrajectory:0.0.0-pre5")
}

application {
    // mainModule = "org.team2363.helixnavigator"
    mainClass.set("org.team2363.helixnavigator.Main")
}

javafx {
    version = "18"
    modules = listOf("javafx.controls")
}

// shadowJar {
//     // minimize()

//     // archiveClassifier.set("")
// }

// launch4j {
//     mainClassName = project.mainClassName
//     version = project.version
//     outfile = "${packageName}.exe"
//     bundledJrePath = "C:\\Program Files\\Java\\jdk-17.0.2\\bin"
//     jarTask = project.tasks.shadowJar
// }

tasks.register("jpackage") {
    dependsOn("shadowJar")

    doLast {
        val jarDir = "${buildDir}/libs"
        val jarName = "${packageName}-all.jar"
        val jarFile = "${buildDir}/libs/${jarName}"
        val javaOptions = "-Djava.library.path=\$APPDIR"
        val javaMainClass = "java"
        val dest = "${buildDir}/package"
        val iconFile = "${buildDir}/resources/main/icon.icns"
        project.exec {
            commandLine("jpackage",
                    "--input", jarDir,
                    "--java-options", javaOptions,
                    "--main-jar", jarName,
                    "--main-class", "org.team2363.helixnavigator.Main",
                    "--dest", dest,
                    "--name", project.name,
                    "--type", "dmg",
                    "--icon", iconFile,
            )
        }
    }
}