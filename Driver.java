import java.util.Scanner;
public class Driver {
    private static int threads = 4;
    private static double x = -0.875, y = 0, zoom = 1;
    
    public static void runMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello!\nWould you like to run the default settings? (y/n)\nThis would produce a picture at 1080p, with no zoom (1x), a center coordinate of (-0.875, 0), and using 4 threads.");
        String input = sc.nextLine();
        if(input.equals("n")){
            System.out.println("Please enter the zoom factor (1x, 1.5x, 2x, 4x, etc):");
            zoom = Double.parseDouble(sc.nextLine());
            System.out.println("Please enter the x coordinate:");
            x = Double.parseDouble(sc.nextLine());
            System.out.println("Please enter the y coordinate:");
            y = Double.parseDouble(sc.nextLine());
            System.out.println("Please enter the number of threads to use (keeping in mind the number of cores and threads of your CPU):");
            threads = Integer.parseInt(sc.nextLine());
        }
    }
    public static void main(String[] args) {
        if(args.length == 0){
            runMenu();
        }
        if(args.length >= 1) {
            zoom = Double.parseDouble(args[0]);
        }
        if(args.length >= 3){
            x = Double.parseDouble(args[1]);
            y = Double.parseDouble(args[2]);
            // System.out.println("x: " + x + " y: " + y);
        }
        if(args.length >= 4){
            threads = Integer.parseInt(args[3]);
        }
        FractalRenderer renderer = new FractalRenderer();
        //32K
        // renderer.renderSetup(15360*2, 8640*2, x, y, zoom, threads);
        // 16K
        // renderer.renderSetup(15360, 8640, x, y, zoom, threads);
        // 4K
        renderer.renderSetup(3840, 2160, x, y, zoom, threads);    
        //1080
        // renderer.renderSetup(1920, 1080, x, y, zoom, threads);
        System.out.println("Program end");
    }
}
