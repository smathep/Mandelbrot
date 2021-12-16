public class Driver {
    public static void main(String[] args) {
        double x = -0.875, y = 0, zoom = 1;
        int threads = 4;
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
