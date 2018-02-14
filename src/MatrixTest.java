import java.util.ArrayList;

public class MatrixTest {

    private static void rowTest(){
        System.out.println("Now testing Row Functions:");
        ArrayList<MatrixRow> rows = new ArrayList<>();
        ArrayList<Double> row1vals = new ArrayList<Double>();
        row1vals.add((Double)2.0);
        row1vals.add((Double)4.0);
        row1vals.add((Double)6.0);
        row1vals.add((Double)3.0);
        rows.add(new MatrixRow(row1vals,0));
        System.out.println(rows.get(0));
        rows.get(0).rowReduce();
        System.out.println(rows.get(0));
    }

    private static void unchangedTest(){
        System.out.println("Now testing an unchanged matrix solution:");
        ArrayList<MatrixRow> rows = new ArrayList<>();
        ArrayList<Double> row1vals = new ArrayList<Double>();
        row1vals.add((Double)1.0);
        row1vals.add((Double)0.0);
        row1vals.add((Double)0.0);
        row1vals.add((Double)2.0);
        rows.add(new MatrixRow(row1vals,0));

        ArrayList<Double> row2vals = new ArrayList<Double>();
        row2vals.add((Double)0.0);
        row2vals.add((Double)1.0);
        row2vals.add((Double)0.0);
        row2vals.add((Double)3.0);
        rows.add(new MatrixRow(row2vals,1));

        ArrayList<Double> row3vals = new ArrayList<Double>();
        row3vals.add((Double)0.0);
        row3vals.add((Double)0.0);
        row3vals.add((Double)1.0);
        row3vals.add((Double)4.0);
        rows.add(new MatrixRow(row3vals,2));

        Matrix newMatrix = new Matrix(rows);

        System.out.println(newMatrix);
        newMatrix.solveMatrix();
        System.out.println(newMatrix);
    }

    private static void rrefTest(){
        System.out.println("Now testing a sample matrix:");

        ArrayList<MatrixRow> rows = new ArrayList<>();
        ArrayList<Double> row1vals = new ArrayList<Double>();
        row1vals.add((Double)2.0);
        row1vals.add((Double)4.0);
        row1vals.add((Double)6.0);
        row1vals.add((Double)3.0);
        rows.add(new MatrixRow(row1vals,0));


        ArrayList<Double> row2vals = new ArrayList<Double>();
        row2vals.add((Double)8.0);
        row2vals.add((Double)1.0);
        row2vals.add((Double)6.0);
        row2vals.add((Double)8.0);
        rows.add(new MatrixRow(row2vals,1));

        ArrayList<Double> row3vals = new ArrayList<Double>();
        row3vals.add((Double)7.0);
        row3vals.add((Double)4.0);
        row3vals.add((Double)4.0);
        row3vals.add((Double)2.0);
        rows.add(new MatrixRow(row3vals,2));

        Matrix newMatrix = new Matrix(rows);

        System.out.println(newMatrix);
        newMatrix.solveMatrix();
        System.out.println(newMatrix);
    }

    public static void main(String[] args) {
        rowTest();
        unchangedTest();
        rrefTest();
    }
}
