compile:
	javac Driver.java FractalRenderer.java RenderThread.java -d bin

package: compile
	jar --create --file Mandelbrot.jar -e Driver -C bin .

#runs default settings
run:
	java -jar Mandelbrot.jar 

clean:
	rm -rf bin
	rm -rf Mandelbrot.jar

clean-images:
	rm -rf images