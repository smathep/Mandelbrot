public class Driver {
    public static void main(String[] args) {
        double x = -0.875, y = 0, zoom = .75;
        if(args.length == 3){
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            zoom = Double.parseDouble(args[2]); 
        }
        FractalRenderer renderer = new FractalRenderer();
        //32K
        // renderer.renderSetup(15360*2, 8640*2, x, y, zoom);
        // 16K
        // renderer.renderSetup(15360, 8640, x, y, zoom);
        // 4K
        // renderer.renderSetup(3840, 2160, x, y, zoom);    
        //1080
        renderer.renderSetup(1920, 1080, x, y, zoom);
        System.out.println("Program end");
    }
}
