# Practice Problems & Explanations

## Unit 1: Sequencing
* Draw a square
  * Use the pen to move forward and turn 90 degrees 4 times, so as to draw a regular square
* Move to a location then point back where you started
  * Simply move forwards then turn 180
* Draw the letter A
  * Draw a capital A, three lines is all that’s needed

## Unit 2: Conditionals
* Call-and-response age asker with an age limit
  * Computer asks user how old they are
  * Person responds with age
  * Computer responds to some age criteria, such as saying “You are a teenager” if the age is between 13 and 19, and saying something else if they are not.
* Higher or Lower
  * User guess
    * The computer chooses a random number and the user has to try and guess it. If the number the user guesses is too low, the computer says “too low” or “higher”. If the guess is too high, the computer says “too high” or “lower”
  * Computer guess
    * Same as the other version, except the computer guesses the numbers and the person says higher or lower.
* In-budget calculator
  * We have a selection of items someone can “purchase” like oranges and bananas and apples, which all cost different amounts. Apples are $1, Oranges are $2, Bananas are $3. You are assumed to have $20, and so the user inputs the number of apples, oranges, and bananas, and the computer says whether you can afford those fruit or not.
* Clock operator (mod)
  * Modulus is a function that is the equivalent of a remainder function. You do an integer division and see what the remainder is, and that’s what you return.
* Movement around screen
  * Use the arrow or WASD keys to move a sprite around the screen

## Unit 3: Logic Operators
* Movement w/ diagonal (two keys pressed together)
  * Same movement system, but if you press two keys at the same time, then you should move diagonally at the same speed, not faster. It’s a tougher challenge than people expect.
* Speed up (shift + movement)
  * Pressing the Shift key while moving makes you move faster.
* Multiple Age range checker (Child OR Elder)
  * Check if a given age number is a teenager (13-18) or an elderly (70+)
* Password requirements maker
  * Ask the user to input a password. The password must have at least one capital letter, one lowercase letter, no spaces, and one of the following characters: (#, !, ?, *, _, -, $, %, @, &)

## Unit 4: Loops
* Draw a square
  * Draw a square using a for loop that loops four times over the same piece of code.
* Higher or Lower (multiple guesses)
  * Now, if the inputted answer is wrong, tell the guesser whether the guess was too high or too low, then give them another guess. Terminate the program when the guesser gets the number correct.
* (Java) How to make a forever loop
  * (hint: try thinking about what a WHILE loop needs to continue running)

## Unit 5: Variables
* Simple Calculator
  * Do addition, subtraction, multiplication, and division with integers
    * Pay special attention to integer division
  * Do addition, subtraction, multiplication, and division with floats and doubles
* Print 1-10
  * Use a loop to iterate 10 times through while printing an integer value
* Factorial
  * Factorial of an integer is that integer multiplied by every integer beneath it down to 1. Therefore, 5! = 5 * 4 * 3 * 2 * 1 = 120. Use a loop to iterate through the multiplications and make the function.
* Pow function (exponentiate)
  * This is simply an exponential function. 5^2 = 5 * 5 = 25, and 5^3 = 5 * 5 * 5 = 125. Use loops to raise one given integer to another given integer.

## Unit 6: Functions and Algorithms
* Turn previous problems into functions
  * Pow
  * Factorial
* Binary Search
  * Binary search utilizes a sorted list to find values much faster than polling each individual entry. This is basically a higher-or-lower method of finding elements in a list. A computer guesses in the middle of the range of possible values, and then based on whether the element it is looking for is higher or lower than its guess, it cuts its search range in half. It then repeats the process until it finds the element it is looking for.

## Unit 7: Data Structures
* Array enter and access functions
  * Make a function to pull values from a list at a given index (Getter)
  * Make a function to enter a value into a list (Setter)
    * Make it put it at the beginning
    * Make it put it at the end
    * Make it put it at a specific index
* Make a 2D array that holds names, ages, and wealth
  * Getter
  * Setter

## Unit 8: Object-Oriented Programming
* Make a custom Person class
  * Name
  * Age
  * Wealth
  * Getters and Setters
* Make a class of your choice with at least 3 types of state
  * Getters and Setters
* Make a class Car
  * Make subclasses to Car called Truck and Van
    * Override necessary methods in subclasses
    * Try and factor out all of the common state and methods, and only add unique methods in the subclasses

## Unit 9: Planning/Program Design
* No exercises for this lesson, use the lessons for the major projects
