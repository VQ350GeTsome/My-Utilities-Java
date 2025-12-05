package ComplexNumbers;


public class Quaternion {
    
    //Scalar + the three imaginary components
    public float s, i, j, k;

    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    /**
     * Default constructor. Initialized this quaternion as
     * a unit quaternion equal to ( 1 , 0 , 0 , 0 ) also known
     * as the identity quaternion.
     */
    public Quaternion() { s = 1; i = 0; j = 0; k = 0; }
    /**
     * Explicit constructor. 
     * 
     * @param s The scalar component.
     * @param i The i component.
     * @param j The j component.
     * @param k The k component.
     */
    public Quaternion(float s, float i, float j, float k) { this.s = s; this.i = i; this.j = j; this.k = k; }
    /**
     * Constructor that uses a float plus a vec3 object to instantiate this. 
     * The x, y, & z will correspond to i, j, & k. The float will be the scalar.
     * 
     * @param s What will be the scalar of this quaternion.
     * @param v The vector to be used for the imaginary components.
     */
    public Quaternion(float s, Vectors.vec3 v) { this.s = s; i = v.x; j = v.y; k = v.z; }
    /**
     * Constructor using a vec4 object. x, y, & z corresponds to i, j, & k.
     * w will be the scalar value.
     * 
     * @param v The vec4 to use. If null this will be equal to the identity quaternion.
     */
    public Quaternion(Vectors.vec4 v) { 
        if (v == null) { s = 1; i = 0; j = 0; k = 0; } 
        else { s = v.w; i = v.x; j = v.y; k = v.z; }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" 4-Function Scalar Operators ">
    public Quaternion add(float f) { return new Quaternion(s + f, i + f, j + f, k + f); }
    public Quaternion subtract(float f) { return this.add( -f ); }
    public Quaternion scale(float f) { return new Quaternion(s*f, i*f, j*f, k*f); }
    public Quaternion scaleImag(float f) { return new Quaternion(s, i*f, j*f, k*f); }
    public Quaternion divide(float f) { return this.scale( 1/f ); }
    public Quaternion divideImag(float f) { return this.scaleImag( 1/f ); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" 4-Function Quaternion Operators ">
    public Quaternion add(Quaternion o) { return new Quaternion(s + o.s, i + o.i, j + o.j, k + o.k); }
    public Quaternion subtract(Quaternion o) { return this.add(o.negate()); }
    public Quaternion multiply(Quaternion o) {
        return new Quaternion(
            s*o.s - i*o.i - j*o.j - k*o.k,
            s*o.i + i*o.s + j*o.k - k*o.j,
            s*o.j - i*o.k + j*o.s + k*o.i,
            s*o.k + i*o.j - j*o.i + k*o.s
        );
    }
    public Quaternion divide(Quaternion o) { return this.multiply(o.inverse()); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Special Scalar Operators ">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Special Quaternion Operators ">
    /**
     * Takes in a vec3 object and rotates it using this quaternion.
     * 
     * @param v
     * @return 
     */
    public Vectors.vec3 rotate(Vectors.vec3 v) {
        if (s == 1 && magnitude() == 1) return v;  //If no rotation just return v.
        Quaternion qv = new Quaternion(0, v.x, v.y, v.z);
        Quaternion res = this.multiply(qv).multiply(this.conjugate());
        return new Vectors.vec3(res.i, res.j, res.k);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Information Calculators ">
    public float magnitude() { return (float) Math.sqrt(this.magnitude()); }
    public float magnitudeSquared() { return s*s + i*i + j*j + k*k; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Transformers ">
    public Quaternion negate() { return this.scale(-1.0f); }
    public Quaternion normalize() {
        float l = this.magnitude();
        return new Quaternion(s / l, i / l, j / l, k / l);
    }
    public Quaternion inverse() {
        float normSq = this.magnitudeSquared();
        if (normSq == 0) throw new ArithmeticException("Cannot invert a zero quaternion");
        Quaternion conj = conjugate();
        return new Quaternion(conj.s / normSq, conj.i / normSq, conj.j / normSq, conj.k / normSq);
    }
    public Quaternion conjugate() { return new Quaternion(s, -i, -j, -k); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" String Methods & Constructor ">
    @Override
    public String toString() { return "{" + s + ":" + i + ":" + j + ":" + k + "}"; }
    public Quaternion(String s) {
        s = s.trim().substring(1, s.length() - 2);   //Trim off grouping character
        String[] comp = s.split("[,\\:]");           //Split by , or :
        
        if (comp.length != 4) {
            throw new IllegalArgumentException("Invalid Quaternion format: " + s);
        }
                
        // Parse and assign
        this.s = Float.parseFloat(comp[0].trim());
        this.i = Float.parseFloat(comp[1].trim());
        this.j = Float.parseFloat(comp[2].trim());
        this.k = Float.parseFloat(comp[3].trim());
    }
    public Quaternion(String[] s) {
        this.s = Float.parseFloat(s[0].trim());
        this.i = Float.parseFloat(s[1].trim());
        this.j = Float.parseFloat(s[2].trim());
        this.k = Float.parseFloat(s[3].trim());
    }
    public String toStringImag() { return "{" + i + ":" + j + ":" + k + "}"; }
    //</editor-fold>
}