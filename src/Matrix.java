import java.util.ArrayList;
import java.util.Observable;

/**
 * Matrix.java
 *
 * A class to represent a linear matrix.
 *
 * @author djp7523
 *
 * TODO: Testing
 * TODO: Support for Non-Square Augmented Matrices: i.e. n x n+1 matrices
 * TODO: Option for Row-Echelon-Form reduction
 */

public class Matrix extends Observable {
    private ArrayList<MatrixRow> rows;

    public Matrix(){
        this.rows = new ArrayList<>();
    }

    public Matrix(ArrayList<MatrixRow> rows){
        this.rows = rows;
    }

    /**
     * Replace the current set of rows with a new set of rows.
     */
    public void changeRows(ArrayList<MatrixRow> rows){
        this.rows = rows;
        this.setChanged();
        this.notifyObservers("");
    }

    /**
     * Forces a row into identity form
     */
    public void makeIdentity(){
        for (MatrixRow i: rows){
            if (i.getVals().get(i.getPosition()) != 1){
                for (Double val : i.getVals()) {
                    i.getVals().set(i.getVals().indexOf(val), 0.0);
                }
                    i.getVals().set(i.getPosition(),1.0);
            }
        }
        System.out.println(this);
        this.setChanged();
        this.notifyObservers("");
    }

    /**
     * Algorithmically attempt to solve a matrix.
     * Current Valid Matrices:
     * Matrix is square augmented (nxn+1)
     */
    public void solveMatrix(){
        boolean inconsistent = false;

        for (MatrixRow i : rows){

            //if row is not solvable based on position, change the positions around
            //assume each row's position is the index of it's row
            if (i.getVals().get(i.getPosition()) == 0){
                boolean foundswap = false;
                for (MatrixRow j: rows){
                    if (j.getVals().get(i.getPosition()) != 0){
                        MatrixRow.swapPosition(i,j);

                        //this could easily cause issues with the rows...
                        foundswap = true;
                        //i = j; yeah that's definitely not right
                        //rows.set(rows.indexOf(i),j);
                        //rows.set(rows.indexOf(j),i);
                        //none of that's nessecary i guess??????
                    }
                    if(foundswap){
                        break;
                    }
                }
                if (!(foundswap)){
                    for (MatrixRow j: rows){
                        if(j.getPosition() >= i.getPosition()){
                            j.setPosition(j.getPosition()+1);
                        }
                    }
                }
            }

            if (!(i.isReduced())){
                i.rowReduce();
            }
            //for each row, try and get it into reduced form

            for (MatrixRow j : rows){
                if (!(i.equals(j))){
                    if (j.getVals().get(i.getPosition()) != 0){
                        j.addRow(i,-j.getVals().get(i.getPosition()));
                    }
                }
            }
            //then for all OTHER rows, if the value in the same column is not zero, clear it with row operations

            if(i.isInconsistent()){
                inconsistent = true;
                break;
            }
            //if a row is inconsistent, break and report
        }
        if (inconsistent){
            this.setChanged();
            this.notifyObservers("Inconsistent System.");
        }
        else{
            this.setChanged();
            this.notifyObservers("");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (MatrixRow row : rows){
            result += row.toString();
            result += "\n";
        }
        return result;
    }

}
