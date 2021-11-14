# How to Set Up Java Library

1. Download and install Git on your computer from [here](https://docs.github.com/en/desktop/installing-and-configuring-github-desktop/installing-and-authenticating-to-github-desktop/installing-github-desktop)
2. Download and install Gradle on your computer from [here](https://gradle.org/install/)
3. Use the Github Desktop to clone the contents of the "code" folder into your desired location on your computer
4. Open a terminal window and navigate to the location you just extracted the code into
5. Type "gradle build" into the command line and hit Enter

# How to use Java Library

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

3. Write your desired code within the "start" function. You can add any more functions you want, just maks sure that they are outside of the start function
4. To run your code, go to the file titled "build.gradle" and open it up
5. At the bottom, type the following code:

```gradle
task NameOfProject(type:JavaExec) {
  classpath = sourceSets.main.runtimeClasspath
  main = 'exercises.NameOfClass'
}
```

6. Finally, to run the code, go into your terminal window, make sure you're in the folder you extracted the code into, and type "gradle NameOfProject" into the command line and hit Enter
