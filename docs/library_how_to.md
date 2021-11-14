# How to Set Up Java Library

1. Download and install Git on your computer from [here](https://git-scm.com/downloads)
2. Download and install Gradle on your computer from [here](https://gradle.org/install/)
3. Use Git to clone the contents of the "code" folder into your desired location on your computer
   1. Decide where you want to put the code
   2. Create the directory and cd into it
   3. Run `git clone https://github.com/srsalisbury/Introductory-Programming-Principles.git`
4. Run `cd Introductory-Programming-Principles/code`
5. Run `gradle build`

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
