# Java Swing Traffic Light Project

A first-year Computer Science Java project focused on GUI programming, animation, and event handling.

This project uses Java Swing to create an animated traffic scene. A car moves across the screen while the user controls the traffic light using either mouse clicks or keyboard input. The program also tracks how many cars have passed and includes a reset button for the counter.

## Features

- Java Swing GUI window
- Custom graphics using `paintComponent()`
- Animated car movement
- Separate `Car` class for car drawing and movement logic
- Mouse controls for traffic light interaction
- Keyboard controls:
  - `g` = green light / go
  - `y` = yellow light / slow down
  - `s` = red light / stop
  - `r` = reset car count
- Live status label showing whether the car should go, slow down, or stop
- Car counter with reset button
- Randomized car colours

## Files

- `Car.java` — handles the car's position, movement, randomized colours, drawing, and status updates
- `Traffic.java` — main GUI, road/traffic-light drawing, mouse controls, keyboard controls, labels, button, and animation logic
- `Project2Runner.java` — runner file used to start the program
