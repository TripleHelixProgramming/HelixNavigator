# HelixNavigator
HelixNavigator is a trajectory optimization-based path planning app. You can currently use it to generate trajectories for FRC swerve robots to follow.

HelixNavigator provides the graphical interface to build autonomous paths, the trajectory optimizer, HelixTrajectory, takes these paths and converts them to trajectories that your robot can follow.

## MacOS Installation

Download this repository to a working directory on your Mac.

### Installing HelixNavigator

First download a [distribution](https://github.com/jlbabilino/HelixTrajectoryJ/releases/) of HelixTrajectory for MacOS. Place `helixtrajectoryj.jar` in the `libs/jar` directory of the HelixNavigator project.

A MacOS app can be built using `jpackage`. The Gradle script (`app/build.gradle.kts`) includes a task called `jpackage` to do this:
```
./gradlew app:jpackage
```
will generate generate a file called `HelixNavigator-$VERSION.dmg`. Install the app by opening the `dmg` and dragging the application into `/Applications`.

### Installing HelixTrajectory

If you try to run the app you've just built, it will crash when trying to generate trajectories. This is because the Java interface will fail to locate the native optimization code. In your distribution of HelixTrajectory, there is a zip file containing `libhelixtrajectory.dylib` and dependencies in a `lib` folder. After installing the app as `/Applications/HelixNavigator.app`, place `libhelixtrajectorycpp.dylib` in `/Applications/HelixNavigator.app/Contents/app`. Then place the dependencies in the `lib` directory into `/Applications/HelixNavigator.app/Contents/runtime/Contents/Home/lib/server`. Now the `HelixTrajectory` Java interface should be able to locate the native code.