import java.util.ArrayList;

/**
 * MatrixRow.java
 *
 * A class file to represent a single row in a linear Matrix.
 *
 */
public class MatrixRow {
    private int position;
    private ArrayList<Double> vals;

    public MatrixRow(ArrayList<Double> vals, int position){
        this.position = position;
        this.vals = vals;
    }

    public ArrayList<Double> getVals() {
        return vals;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Checks to see if a row is in reduced form.
     * @return if the row is properly reduced.
     */
    public boolean isReduced(){
        return vals.get(position) == 1;
    }

    public static void swapPosition(MatrixRow one, MatrixRow two){
        int temp1 = two.getPosition();
        two.position = one.getPosition();
        one.position = temp1;
    }

    public boolean isFreeVar(){
        boolean free= true;
        for (double val: vals){
            free &= (val == 0);
        }
        return free;
    }

    public boolean isInconsistent(){
        boolean inconsistent = false;
        if(vals.get(vals.size()-1) != 0){
            inconsistent = true;
            ArrayList<Double> copy = new ArrayList<>(vals);
            copy.remove(copy.size()-1);
            for (double val : copy){
                inconsistent &= (val == 0);
            }
        }
        return inconsistent;
    }

    /**
     * Multiplies a row by a given double.
     * @param multiplier the double to multiply by
     */
    private void multiplyRow(double multiplier){
        for (int i = 0; i < vals.size(); i++){
            vals.set(i,vals.get(i)*multiplier);
        }
    }

    /**
     * Adds another row in some quantity to this row.
     * @param other The row to be added to this one.
     * @param multiplier The multiplier of other.
     */
    public void addRow(MatrixRow other, double multiplier){
        for (int i = 0; i < vals.size(); i++){
            vals.set(i,vals.get(i)+(multiplier * other.vals.get(i)));
        }
    }

    /**
     * Performs a row reduction operation based on the current row.
     */
    public void rowReduce(){
        if (!(isReduced())){
            multiplyRow(1/vals.get(position));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MatrixRow)){
            return false;
        }
        MatrixRow objRow = (MatrixRow)(obj);
        return objRow.vals.equals(this.vals) && objRow.position == this.position;
    }

    @Override
    public String toString() {
        String result = "";
        for (double val : vals){
            result += val;
            result += ", ";
        }
        return result;
    }
}
