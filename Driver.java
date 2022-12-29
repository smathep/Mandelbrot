import java.util.Scanner;
public class Driver {
    private static int threads = 4, resolutionSelection = 1;
    private static double x = -0.875, y = 0, zoom = 1;

    public static void runMenu(){
        Scanner sc = new Scanner(System.in);
        String input;
        boolean userInputValid = false;
        System.out.println("Hello!\n\n");
        do{
            System.out.println("Would you like to run the default settings? (y/n)\nThis would produce a picture at 1080p, with no zoom (1x), a center coordinate of (-0.875, 0), and using 4 threads.");
            input = sc.next();
            if(input.toLowerCase().charAt(0) != 'y' && input.toLowerCase().charAt(0) != 'n'){
                System.out.println("Invalid input, please try again.\n");
            }else{
                userInputValid = true;
            }
        }while(!userInputValid);
        
        if(input.equals("n")){
            zoom = validateInputDouble("Please enter the zoom factor (1x, 1.5x, 2x, 4x, etc):", sc);
            x = validateInputDouble("Please enter the x coordinate:", sc);
            y = validateInputDouble("Please enter the y coordinate:", sc);
            threads = validateInputInt("Please enter the number of threads to use (keeping in mind the number of cores and threads of your CPU):", sc);
            resolutionSelection = validateInputInt("Please select a resolution:\n1. 1080 (1920x1080)\n2. 4K (3840x2160)\n3. 16K (15360x8640)\n4. 32K (30720x17280)", sc, 1, 4);
        }
        sc.close();
        
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
        }
        if(args.length >= 4){
            threads = Integer.parseInt(args[3]);
        }
        if(args.length >= 5){
            resolutionSelection = Integer.parseInt(args[4]);
        }
        FractalRenderer renderer = new FractalRenderer();
        switch(resolutionSelection){
            case 1:
                //1080
                renderer.renderSetup(1920, 1080, x, y, zoom, threads);
                break;
            case 2:
                //4K
                renderer.renderSetup(3840, 2160, x, y, zoom, threads);
                break;
            case 3:
                //16K
                renderer.renderSetup(15360, 8640, x, y, zoom, threads);
                break;
            case 4:
                //32K
                renderer.renderSetup(30720, 17280, x, y, zoom, threads);
                break;
        }
        System.out.println("Program end");
    }

    public static double validateInputDouble(String prompt, Scanner sc){
        double input = 0.0;
        boolean userInputValid = false;
        do{
            System.out.println(prompt);
            try{
                input = Double.parseDouble(sc.next());
                userInputValid = true;
            }catch(NumberFormatException e){
                System.out.println("Invalid input, please try again.\n");
                continue;
            }
        }while(!userInputValid);
        return input;
    }

    public static int validateInputInt(String prompt, Scanner sc){
        int input = 0;
        boolean userInputValid = false;
        do{
            System.out.println(prompt);
            try{
                input = Integer.parseInt(sc.next());
                userInputValid = true;
            }catch(NumberFormatException e){
                System.out.println("Invalid input, please try again.\n");
                continue;
            }
        }while(!userInputValid);
        return input;
    }

    public static int validateInputInt(String prompt, Scanner sc, int min, int max){
        int input = 0;
        boolean userInputValid = false;
        do{
            input = validateInputInt(prompt, sc);
            if(input < min || input > max){
                System.out.println("Invalid input, please try again.\n (Number should be between " + min + " and " + max + ".\n");
            }else{
                userInputValid = true;
            }
        }while(!userInputValid);
        return input;
    }
}
