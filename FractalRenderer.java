import java.awt.FlowLayout;
import java.awt.Image;
// import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//https://www.javamex.com/tutorials/graphics/bufferedimage_setrgb.shtml
// https://en.wikipedia.org/wiki/Mandelbrot_set#Computer_drawings
// https://blog.bede.io/how-to-render-a-fractal-fast/

public class FractalRenderer{
    public final int MAX_ITERATION = 5000;
    public final int DISPLAY_WIDTH = 1920;
    public final int DISPLAY_HEIGHT = 1080;
    private Path cwd;
    private int width, height;
    private BufferedImage img;
    private int threadCount;
    private AtomicInteger nextRenderSection = new AtomicInteger(threadCount);
    private double centerX; //default: -.875
    private double centerY; //default: 0
    private double zoomFactor; //default: 1
    private JFrame frame;
    private JLabel label;
    
    public FractalRenderer(){
        Path cwdTest = Paths.get(System.getProperty("user.dir") + "/images");
        if(Files.notExists(cwdTest, LinkOption.NOFOLLOW_LINKS)){
            try{
                Files.createDirectories(cwdTest);
            }catch(IOException e){
                System.out.println("IO Exception Occured. Program wll exit.");
                System.exit(1);
            }
        }
        frame = new JFrame("Mandelbrot");
        label = new JLabel();
        cwd = cwdTest;
    }
    
    private void displayFractal(){
        //had to scale the sizes down slightly to fit the entire set in the GUI window, 0.79 just happened to be the best scaling factor
        label.setIcon(new ImageIcon(img.getScaledInstance((int)(DISPLAY_WIDTH*0.79), (int)(DISPLAY_HEIGHT*0.79), Image.SCALE_FAST)));
        // label.setIcon(new ImageIcon(img));
        frame.setVisible(true);

    }

    public boolean renderSetup(int width, int height, double cX, double cY, double zoom, int threadC){
        this.width = width;
        this.height = height;
        centerX = cX;
        centerY = cY;
        zoomFactor = zoom;
        threadCount = threadC;
        long startTime = System.currentTimeMillis();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        RenderThread[] threads = new RenderThread[threadCount];
        int numSections = threadCount*2;

        ImageIcon imageicon = new ImageIcon(img);

        frame.setLayout(new FlowLayout());
        // frame.setSize(img.getWidth(), img.getHeight());
        frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        
        label.setIcon(imageicon);
        frame.getContentPane().add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(;)

        for(int tCount = 0; tCount < threadCount; tCount++){
            threads[tCount] = new RenderThread(cwd, "r-thread" + tCount, img, width, height, this);
            threads[tCount].setStartX(0);
            threads[tCount].setStartY((height / numSections) * tCount);
            threads[tCount].setEndX(width);
            threads[tCount].setEndY((height / numSections) * (tCount+1));
            threads[tCount].start(); 
        }

        try{
            for(RenderThread t : threads){
                t.t.join();
            }

        }catch(InterruptedException e){
            System.out.println("Interrupted thread");
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy-HH_mm_ss");
        File f = new File(cwd.toString() + "/" + formatter.format(Calendar.getInstance().getTime()) + ".png");
        try{
            ImageIO.write(img, "PNG", f);
            System.out.println("Completed in " + (System.currentTimeMillis() - startTime) + " miliseconds.");
        }catch(IOException e){
            System.out.println("IO Exception Occured. Program wll exit.");
            System.exit(1);
        }
        return true;
    }


    public void setPixel(int px, int py){
        double xToYRatio = 1.875;
        double xScaleLower = centerX - xToYRatio / zoomFactor;
        double xScaleUpper = centerX + xToYRatio / zoomFactor;
        double yScaleLower = centerY - (Math.abs(xScaleUpper - xScaleLower) / 2 / xToYRatio);
        double yScaleUpper = centerY + (Math.abs(xScaleUpper - xScaleLower) / xToYRatio / 2);
        double x0 = scale(px, width, xScaleLower, xScaleUpper); //-2.75, 1
        double y0 = scale(py, height, yScaleLower, yScaleUpper); //-1, 1       Ratio for x to y: 1.875
        double x = 0.0, y = 0.0;
        int iteration = 0;
        int bailout  = 4;
        while(x*x + y*y <= bailout && iteration < MAX_ITERATION){
            double xtemp = x*x - y*y + x0;
            y = 2*x*y + y0;
            x = xtemp;
            iteration++;
        }
        // System.out.println("Iteration: " + iteration);

        int r = (int)scale(Math.pow(iteration, 4), MAX_ITERATION, 0, 255);// red component 0...255
        int g = (int)scale(Math.pow(iteration, 3), MAX_ITERATION, 0, 255);// green component 0...255
        int b = (int)scale(Math.pow(iteration, 3), MAX_ITERATION, 0, 255);// blue component 0...255
        // int r = (int)scale(iteration, MAX_ITERATION, 0, 255);// red component 0...255
        // int g = (int)scale(iteration, MAX_ITERATION, 0, 255);// green component 0...255
        // int b = (int)scale(iteration, MAX_ITERATION, 0, 255);// blue component 0...255
        // int a = (int) (255);// alpha (transparency) component 0...255
        // builds an RGB int value
        int rgb = (r << 16) | (g << 8) | b;
        // img.setRGB(px, py, (int)scale(Math.pow(rgb, 2), MAX_ITERATION, 0, 255));
        
        img.setRGB(px, py, rgb);    
        
    }

    public double scale(double n, int max, double minPrime, double maxPrime){
        return (n / max) * (maxPrime - minPrime) + minPrime;
    }

    public boolean startNextSection(RenderThread rt){
        displayFractal();

        if(nextRenderSection.get() < (threadCount * 2)){
            // System.out.println(rt.tName + " getting new section");
            // System.out.println("nextRenderSection: " + nextRenderSection.get());
            System.out.println(rt.tName + " Finished rendering section. Getting section " + nextRenderSection.get());
            rt.setStartY((height / (threadCount*2)) * nextRenderSection.get());
            rt.setEndY((height / (threadCount*2)) * nextRenderSection.incrementAndGet());
            return true;
        }
        return false;
    }

}