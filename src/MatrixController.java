import java.util.ArrayList;

public class MatrixController {
    public Matrix matrix;

    public MatrixController(Matrix m){
        this.matrix = m;
    }

    public void changeMatrix(ArrayList<MatrixRow> rows){
        matrix.changeRows(rows);
        matrix.solveMatrix();
    }
}
