# Mandelbrot Renderer

Simple Mandelbrot Set visualizer
Allows the user to customize the zoom scale, centered coordinates, and number of threads used for rendering

# To-do list:
- Implement nice command-line driven menue system for allowing user to specify arguments during runtime outside of command line arguments
- (Eventually) implement GUI for controlling render settings
- Allow the user to specify the resolution outside of editing the Driver.java class
- Allow the color scheme to be chosen by the user or adjusted in some way
- Allow arguments in the makefile run command to be passed to the java run command

# Command usage
- compile: 
```make compile
make package
```
- run: 
```
make run //requires the jar file to be made and runs with default parameters
java Driver 1 //specifies zoom
java Driver 1 -0.875 1 //specifies zoom and coordinates
java Driver 1 -0.875 1 4 //specifies zoom, coordinates, and thread count
```

# Rendered Images
![1639696289178](https://user-images.githubusercontent.com/71954677/146462631-ffb40527-e39c-44f7-95f7-7765dc6eac18.png)
- 1080p at default zoom and coordinates

![1639696779325](https://user-images.githubusercontent.com/71954677/146463168-73e8452d-f484-4b76-a79d-d1a7b62b35cf.png)
- 4K image at 6x zoom at (-1, 0.25)

![1639696930435](https://user-images.githubusercontent.com/71954677/146463474-5a6be172-fda9-4dd8-98d4-ece2f79bae06.png)
- 4K image at 25x zoom at (-1, 0.35)

