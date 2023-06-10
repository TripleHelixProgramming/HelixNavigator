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

version = "1.0.1"

val packageName = "${name}-${version}"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://maven.pkg.github.com/SleipnirGroup/TrajoptLib")
        credentials {
            username = project.properties["mvnUsername"].toString()
            password = project.properties["mvnPassword"].toString()
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

tasks.register("jpackage") {
    dependsOn("shadowJar")

    val os = System.getProperty("os.name").toLowerCase();

    var packType = "";

    if (os.startsWith("linux")) {
        packType = "deb";
    } else if (os.startsWith("mac")) {
        packType = "dmg"
    } else { // windows
        packType = "msi"
    }

    doLast {
        project.exec {

            if (os.startsWith("linux")) {
                commandLine("jpackage",
                    "--input", "${buildDir}/libs",
                    "--main-jar", "${packageName}-all.jar",
                    "--main-class", "org.team2363.helixnavigator.Main",
                    "--type", packType,
                    "--dest", "${buildDir}/package",
                    "--name", rootProject.name,
                    "--app-version", version,
                    "--icon", "${buildDir}/resources/main/icon.icns",
                    linuxShortcut,
                )
            } else {
                commandLine("jpackage",
                        "--input", "${buildDir}/libs",
                        "--main-jar", "${packageName}-all.jar",
                        "--main-class", "org.team2363.helixnavigator.Main",
                        "--type", packType,
                        "--dest", "${buildDir}/package",
                        "--name", rootProject.name,
                        "--app-version", version,
                        "--icon", "${buildDir}/resources/main/icon.icns",
                )
            }
        }
    }
}

tasks.register("printVersion") {
  println(project.version)
}