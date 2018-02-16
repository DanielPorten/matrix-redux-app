import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class MatrixGUI extends Application implements Observer{

    private enum parseStates{
        SOLVE, IDENTITY, ZERO;
    }

    private MatrixController controller;
    private Stage primaryStage;
    private Scene primaryScene;
    private Scene secondaryScene;
    private BorderPane bpane;
    private GridPane matrixPane;
    private int dimension;
    private Text message;

    public MatrixGUI(){
        Matrix matrix = new Matrix();
        matrix.addObserver(this);
        this.controller = new MatrixController(matrix);
    }

    private String intParsing(String d){
        String returnVal = d;
        if(Double.parseDouble(d) % 1 == 0){
            Double j = Double.parseDouble(d);
            returnVal = Integer.toString( j.intValue() );
        }
        return returnVal;
    }

    @Override
    public void update(Observable o, Object arg) {
        //testing
        System.out.println(o);

        String [] rows = o.toString().split("\n");
        int counter = 0;
        int valpos = 0;
        for (String row : rows){
            String[] vals = row.split(",");
                while (counter < dimension && valpos < dimension+1) {
                    for (Node field : matrixPane.getChildren()) {
                        if (counter == matrixPane.getChildren().indexOf(field) % dimension) {
                            TextField f = (TextField) (field);
                            f.setText(intParsing(vals[valpos]));
                            valpos++;
                        }
                    }
                }
                valpos = 0;
                counter++;
            }
        message.setText(arg.toString());
    }

    private void switchScene(Scene switchTo){
        primaryStage.setScene(switchTo);
        primaryStage.show();
    }

    private void parseMatrix(parseStates state){
        int counter=0;
        ArrayList<MatrixRow> rows = new ArrayList<>();
        boolean invalid = false;

        while (counter < dimension){
            ArrayList<Double> vals = new ArrayList<>();
            for (Node field : matrixPane.getChildren()){
                if (counter == matrixPane.getChildren().indexOf(field)%dimension){
                    TextField f = (TextField)(field);

                    try {
                        vals.add(Double.parseDouble(f.getText()));
                    }
                    catch (NumberFormatException e){
                        invalid = true;
                    }
                }
            }

            if(vals.size()!=(dimension+1)){
                invalid = true;
            }
            if(!(invalid)) {
                rows.add(new MatrixRow(vals, counter));
                counter++;
            }
            else{
                message.setText("Invalid Matrix.");
            }
        }
        switch (state){
            case SOLVE:
                controller.solveMatrix(rows);
            case IDENTITY:
                controller.forceIdentity(rows);
        }
    }

    private void startMatrix(TextField textField){
        dimension = -1;

        while (dimension == -1){
            int temp = -1;
            try{
                temp = Integer.parseInt(textField.getText());
            }
            catch (NumberFormatException exception){
            }
            if (temp < 1){
                //how to wait on proper input?
            }
            else{ dimension = temp; }

        }

        matrixPane = new GridPane();

        for (int i = 0; i <= dimension; i++){
            for (int j = 0; j < dimension; j++){
                matrixPane.add(new TextField("0"),i,j);
                // make it so that i can add hboxes with label and field
            }
        }

        matrixPane.setHgap(10);
        matrixPane.setVgap(10);

        bpane.setCenter(matrixPane);

        switchScene(secondaryScene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        /*
        Creates Matrix Panel
         */

        Button solveButton = new Button("Solve");
        solveButton.setPrefWidth(50);
        solveButton.setOnAction(( ActionEvent event ) -> parseMatrix(parseStates.SOLVE));

        Button backButton = new Button("Back");
        backButton.setPrefWidth(50);
        backButton.setOnAction(( ActionEvent event ) -> switchScene(primaryScene));

        Button identButton = new Button("Identity Matrix");
        identButton.setOnAction(( ActionEvent event ) -> parseMatrix(parseStates.IDENTITY));

        message = new Text("Enter the coefficients of the matrix.");

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(solveButton,backButton,identButton);
        buttonBox.setSpacing(5.0);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        bpane = new BorderPane();
        bpane.setRight(buttonBox);

        bpane.setTop(message);
        this.secondaryScene = new Scene(bpane);

        /*
        Creates Dimension Input
         */

        Text dimText = new Text("Enter the number of variables in your linear system:");

        TextField dimField = new TextField();
        dimField.setOnAction((ActionEvent event) -> startMatrix(dimField));

        VBox dimentryBox = new VBox();
        dimentryBox.getChildren().addAll(dimText,dimField);

        this.primaryScene = new Scene(dimentryBox);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("LAMR");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //TODO: Update
}
