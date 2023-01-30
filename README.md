# HelixNavigator
HelixNavigator is a trajectory optimization-based path planning app. You can currently use it to generate trajectories for FRC swerve robots to follow.

HelixNavigator provides the graphical interface to build autonomous paths, the trajectory optimizer, HelixTrajectory, takes these paths and converts them to trajectories that your robot can follow.

## Linux and MacOS from source

There are currently no binary distributions, so the app must be built from source to run.

Unfortunately, GitHub Actions does not support publishing Maven artifacts without authentication, so you will need a GitHub access token to build with these commands. You will have to build [TrajoptLib](https://github.com/SleipnirGroup/TrajoptLib) from source.

First, clone the repo in your working directory:
```
git clone https://github.com/TripleHelixProgramming/HelixNavigator.git
```

Run the following to build the code and run it:
```
./gradlew run
```

## Build packaged distribution:

Run the following to build a `.dmg` or equivalent Linux package:

```
./gradlew jpackage
```
