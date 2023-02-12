# HelixNavigator

A trajectory optimization-based path planning app for FRC.

![Build](https://github.com/TripleHelixProgramming/HelixNavigator/actions/workflows/build.yml/badge.svg)
[![Discord](https://img.shields.io/discord/975739302933856277?color=%23738ADB&label=Join%20our%20Discord&logo=discord&logoColor=white)](https://discord.gg/ad2EEZZwsS)

## Compatibility

HelixNavigator is compatible with FRC swerve drive robots only. The app generates json trajectories which can be followed by the a trajectory follower in the robot code.

## TrajoptLib

HelixNavigator is backed by the [TrajoptLib](https://github.com/SleipnirGroup/TrajoptLib), a C++ library for generating time-optimal trajectories.

## Binary Installation

You can download the latest binaries on [Releases](https://github.com/TripleHelixProgramming/HelixNavigator/releases).

### Linux

Use your system's package manager to install the `HelixNavigator-X.X.X-linux-x64.deb` artifact.

### macOS

Download the `HelixNavigator-X.X.X-macOS-x64.zip` artifact. Extract it and place in the `/Applications` directory.

### Windows

**DISCLAIMER:** The Windows distribution does not support generation of trajectories. This is an ongoing issue which will be fixed in the future.

Download the `HelixNavigator-X.X.X-windows-x64.msi` artifact and install it.

## Building from source

First, clone the repo in your working directory:
```
git clone https://github.com/TripleHelixProgramming/HelixNavigator.git
```

If on Windows, replace `./gradlew` with `.\gradlew.bat`.

To build:
```
./gradlew build -PmvnUsername=YOUR_USERNAME -PmvnPassword=YOUR_GITHUB_TOKEN
```

To run:
```
./gradlew run
```

## Build packaged distribution

Run the following to build a `.deb`, `.dmg`, or `.msi` package:

```
./gradlew jpackage
```
