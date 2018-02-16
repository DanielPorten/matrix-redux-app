import java.util.ArrayList;

public class MatrixController {
    public Matrix matrix;

    public MatrixController(Matrix m){
        this.matrix = m;
    }

    public void forceIdentity(ArrayList<MatrixRow> rows){
        matrix.changeRows(rows);
        matrix.makeIdentity();
    }

    public void solveMatrix(ArrayList<MatrixRow> rows){
        matrix.changeRows(rows);
        matrix.solveMatrix();
    }
}
