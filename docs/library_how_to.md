# How to Set Up Java Library

1. Download and install Git on your computer from [here](https://git-scm.com/downloads)
2. Download and install Gradle 7.3 on your computer from [here](https://gradle.org/install/)
3. Make sure you have Java 17.0.1 or better installed on your computer
4. Use Git to clone the contents of the "code" folder into your desired location on your computer
   1. Decide where you want to put the code
   2. Create the directory and cd into it
   3. Run `git clone https://github.com/srsalisbury/Introductory-Programming-Principles.git`
5. Run `cd Introductory-Programming-Principles/code`
6. Run `gradle build`

# How to start a project

1. Make a new file in the "exercises" folder, and name your file what you want to call the project
2. Type the following lines into your file:

```java
package exercises;

import ipp.SnapApp;
import ipp.Sprite;

public class NameOfClass extends SnapApp {
  
  @Override
  public void start() {
  
  }
}
```

3. Write your desired code within the "start" function. You can add any more functions you want, just make sure that they are outside of the start function

# How to use Java Library

* Within the "start" method, you can write your program
* First, initialize a Sprite using the command:

```java
Sprite spriteName = addSprite("spriteName");
```

* Then, for each sprite, you need to initialize a thread
* To do this, write:

```java
addThread(() -> {
// put your commands in here
});
```

* To call a command on a sprite, write:

```java
spriteName.method(inputs);
```

* Sprite Methods
  * moveTo(Location loc)
  * glideTo(Location loc, double secs)
  * setDirection(double dir)
  * pointAt(Location loc)
  * turnLeft(double deg)
  * turnRight(double deg)
  * moveForward(double dist)
  * penUp()
  * penDown()
  * setPen(boolean isDown)
  * setPenColor(Color color)
  * setPenSize(double size)
  * setImage(String path, double height, double width)
  * wait(double secs)
  * isTouching(Sprite other)
  * getLocation()
  * getDirection()
  * getHitBoxHeight()
  * getHitBoxWidth()
  * isPenDown()
  * getPenColor()
  * getPenSize()
  * getImage()

# How to run projects

1. To run your code, go to the file titled "build.gradle" and open it up
2. At the bottom, type the following code:

```gradle
task NameOfProject(type:JavaExec) {
  classpath = sourceSets.main.runtimeClasspath
  main = 'exercises.NameOfClass'
}
```

3. Finally, to run the code, go into your terminal window, make sure you're in the folder you extracted the code into, and type "gradle NameOfProject" into the command line and hit Enter
