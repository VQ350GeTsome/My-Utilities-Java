package Matrices;

public class Matrix {

    // The matrix.
    private float[][] mat;
    // The row & column count.
    private int r, c;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Matrix(int size) { mat = new float[size][size]; r = size; c = size; }
    public Matrix(int rows, int columns) { mat = new float[rows][columns]; r = rows; c = columns; }
    public Matrix(Matrix copy) {
        this(copy.r, copy.c);
        for (int i = 0; copy.r > i; i++) for (int j = 0; copy.c > j; j++) 
            mat[i][j] = copy.mat[i][j];
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Getters ">
    public int getRows() { return r; }
    public int getColumns() { return c; }
    public float getValue(int i, int j) throws ArithmeticException {
        //Check bounds
        if (i > r - 1 || 0 > i) throw new ArithmeticException("Row index, " + i + ", out of bounds!");
        if (j > c - 1 || 0 > j) throw new ArithmeticException("Column index, " + j + ", out of bounds!");
        //If it's valid return the data.
        return mat[i][j];
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Simple Scalar Operations ">
    public Matrix scale(float f) {
        Matrix result = new Matrix(this);
        for (int i = 0; this.r > i; i++) for (int j = 0; this.c > j; j++) 
            result.mat[i][j] *= f;
        return result;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Simple Matrix Operations ">
    public Matrix add(Matrix o) throws ArithmeticException {
        if (!this.sizeEquals(o)) throw new ArithmeticException("Matrices must be the same size to add ...");
        Matrix result = new Matrix(this);
        for (int i = 0; this.r > i; i++) for (int j = 0; this.c > j; j++) 
            result.mat[i][j] += o.mat[i][j];
        return result;
    }
    public Matrix subtract(Matrix o) throws ArithmeticException { return this.add(o.negate()); }
    public Matrix multiply(Matrix o) throws ArithmeticException { 
        // Can we actually multiply them?
        // The columns of this matrix must 
        // equal the rows of the other.
        if (this.c != o.r) 
            throw new ArithmeticException
                (
                    "Cannot multiply these matrices ... \n" +
                    "Column count of this matrix: " + this.c + "\n" +
                    "Row count of the other matrix: " + o.r + "\n"
                );
        Matrix result = new Matrix(this.r, o.c);
        
        for (int i = 0; this.r > i; i++) for (int j = 0; o.r > j; j++) 
            for (int k = 0; this.c > k; k++) {
                result.mat[i][j] += this.mat[i][k] * o.mat[k][j];
            }
        return result;
    }
    public Matrix hadamardProduct(Matrix o) throws ArithmeticException {
        if (!this.sizeEquals(o)) throw new ArithmeticException("Matrices must be the same size to calculate the Hadamard Product ...");
        Matrix result = new Matrix(this);
        for (int i = 0; this.r > i; i++) for (int j = 0; this.c > j; j++) 
            result.mat[i][j] *= o.mat[i][j];
        return result;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Transformers ">
    public Matrix negate() { return this.scale(-1.0f); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Equal Operators ">
    public boolean sizeEquals(Matrix o) { return this.r == o.r && this.c == o.c; }
    @Override 
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) return false;
        if (this == obj) return true;
        Matrix o = (Matrix) obj;
        return this.equals(o);
    }
    public boolean equals(Matrix o) {
        for (int i = 0; this.r > i; i++) for (int j = 0; this.c > j; j++) 
            if (this.mat[i][j] != o.mat[i][j]) return false;
        return true;
    }
    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc=" Overrides ">
    @Override
    public int hashCode() {
        return -1;
    }
    //</editor-fold>  
    
}
