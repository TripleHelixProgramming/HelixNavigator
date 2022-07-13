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
}

dependencies {
    implementation("com.github.jlbabilino:json:0.1.4")
    // implementation "javax.measure:unit-api:2.1.3"
    // implementation "tec.units:unit-ri:1.0.3"
    // implementation "tech.units:indriya:2.1.3"
    implementation("systems.uom:systems-unicode:2.1")
    implementation("systems.uom:systems-common:2.1")
    implementation("systems.uom:systems-quantity:2.1")
    // implementation "org.djunits:djunits:4.01.07"

    implementation(files("../libs/jar/helixtrajectoryj.jar"))
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

//     archiveClassifier.set("")
// }

// launch4j {
//     mainClassName = project.mainClassName
//     version = project.version
//     outfile = "${packageName}.exe"
//     bundledJrePath = "C:\\Program Files\\Java\\jdk-17.0.2\\bin"
//     jarTask = project.tasks.shadowJar
// }

// task createDmg {
//     dependsOn shadowJar
    
//     doLast {
//         def jarDir = "${buildDir}/libs"
//         def jarName = "${packageName}.jar"
//         def jarFile = "${buildDir}/libs/${jarName}"
//         def javaOptions = "-Djava.library.path=\$APPDIR"
//         def javaMainClass = "java"
//         def dest = "${buildDir}/package"
//         def iconFile = "${buildDir}/resources/main/icon.icns"
//         project.exec {
//             commandLine("jpackage",
//                     "--input", jarDir,
//                     "--java-options", javaOptions,
//                     "--main-jar", jarName,
//                     // "--main-class", "org.team2363.helixnavigator.Main",
//                     "--dest", dest,
//                     "--name", project.name,
//                     "--type", "dmg",
//                     "--icon", iconFile,
//                     )
//         }
//     }
// }