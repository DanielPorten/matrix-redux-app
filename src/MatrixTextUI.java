import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class MatrixTextUI implements Observer{

    private MatrixController controller;

    public MatrixTextUI(){
        Matrix matrix = new Matrix();
        matrix.addObserver(this);
        this.controller = new MatrixController(matrix);
    }

    public void mainLoop(){
        Scanner in = new Scanner(System.in);
        String line;
        int dimension = -1;
        System.out.println("Welcome to MatrixTextUI.\n");
        System.out.println("Current restrictions on the system are:");
        System.out.println("Matrix must be square with one augmented column.");
        System.out.println("Enter the number of variables in your matrix system (Max. 6):");

        while (dimension == -1){
            line = in.nextLine();
            int temp = -1;
            try{
                temp = Integer.parseInt(line);
            }
            catch (NumberFormatException n){
            }

            if (temp>6 || temp < 1){
                System.out.println("Invalid dimension value.");
            }
            else{ dimension = temp; }
        }
        //get matrix dimension

        System.out.println("Matrix Size: "+ dimension+"x"+dimension);
        System.out.println("Row Size: " + (dimension+1));

        int counter = 0;
        ArrayList<MatrixRow> rows = new ArrayList<>();
        while (counter < dimension){

            boolean invalid = false;

            System.out.println("Enter row #"+ (counter+1)+", separated by commas.");
            line = in.nextLine();
            String[] strings = line.split(",");
            ArrayList<Double> vals = new ArrayList<>();

            for (String s: strings){
                try{
                    vals.add(Double.parseDouble(s));
                }
                catch(NumberFormatException n){
                    invalid = true;
                }
            }
            //reject non-double arguments

            if(vals.size()!=(dimension+1)){
                invalid = true;
            }
            //reject incomplete rows

            if(!(invalid)){
                rows.add(new MatrixRow(vals,counter));
                counter++;
            }
            else {
                System.out.println("Invalid Row.");
            }

        }
        //build rows
        System.out.println("");

        controller.changeMatrix(rows);
        //insert rows to controller
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO: reports matrix when not solvable
        System.out.println(o);
        System.out.println(arg);
    }

    public static void main(String[] args) {
        MatrixTextUI UI = new MatrixTextUI();
        UI.mainLoop();
    }
}
