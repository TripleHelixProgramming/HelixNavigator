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

### Windows WSL

Since the app doesn't allow trajectory generation in windows, you should run it in WSL instead.

[Install Ubuntu WSL](https://ubuntu.com/tutorials/install-ubuntu-on-wsl2-on-windows-10) on your Windows machine.

Next, ensure you have GUI support enabled, following [this tutorial](https://learn.microsoft.com/en-us/windows/wsl/tutorials/gui-apps).

Open an Ubuntu WSL terminal and install HelixNavigator (this is using `v1.0.1`, but you can replace with any version):

```
wget https://github.com/TripleHelixProgramming/HelixNavigator/releases/download/v1.0.1/helixnavigator_1.0.1-1_amd64.deb
sudo dpkg -i helixnavigator_1.0.1-1_amd64.deb
```

After it installs, you can run `/opt/helixnavigator/bin/HelixNavigator` to open the app.

------

### Windows (native, not working)

**DISCLAIMER:** The Windows distribution does not support generation of trajectories. This is an ongoing issue which will be fixed in the future. For now, you can run the app in WSL, see above.

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
