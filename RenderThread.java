import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class RenderThread implements Runnable {
    public Thread t;
    public final Path cwd;
    public final String tName;
    // private BufferedImage img;
    public final int width, height;
    private int startX, endX, startY, endY;
    private FractalRenderer controller;

    public RenderThread(Path currentworkingdirectory, String threadName, BufferedImage i, int width, int height, FractalRenderer fr){
        System.out.println("Thread created");
        cwd = currentworkingdirectory;
        tName = threadName;
        // img = i;
        this.width = width;
        this.height = height;
        controller = fr;

    }

    @Override
    public void run() {
        // System.out.println(tName + " Running"); //debug
        threadRender();
    }

    public void start(){
        // System.out.println(tName + " Starting"); //debug
        if(t == null){
            t = new Thread(this, tName);
            t.start();
        }else{
            run();
        }
    }

    public void threadRender(){
        do{
            // System.out.println("Thread " + t.getName() + " rendering"); //debug
            for(int x = startX; x < endX; x++){
                for(int y = startY; y < endY; y++){
                controller.setPixel(x, y);
                }
            }
            // System.out.println("Thread " + t.getName() + " finished rendering"); //debug
        }while(controller.startNextSection(this));
    }

    public void setStartX(int x){
        startX = x;
    }

    public void setEndX(int x){
        endX = x;
    }

    public void setStartY(int y){
        startY = y;
    }

    public void setEndY(int y){
        endY = y;
    }
}
