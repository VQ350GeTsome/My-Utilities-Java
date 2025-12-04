package ComplexNumbers;

import Vectors.vec3;

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
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" 4-Function Scalar Operators ">
    public Quaternion scaleImag(float f) { return new Quaternion(s, i*f, j*f, k*f); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" 4-Function Quaternion Operators ">
    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
            s*q.s - i*q.i - j*q.j - k*q.k,
            s*q.i + i*q.s + j*q.k - k*q.j,
            s*q.j - i*q.k + j*q.s + k*q.i,
            s*q.k + i*q.j - j*q.i + k*q.s
        );
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Special Scalar Operators ">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Special Quaternion Operators ">
    public vec3 rotate(vec3 v) {
        if (s == 1 && length() == 1) return v;  //If no rotation just return v.
        Quaternion qv = new Quaternion(0, v.x, v.y, v.z);
        Quaternion res = this.multiply(qv).multiply(this.conjugate());
        return new vec3(res.i, res.j, res.k);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Information Calculators ">
    public float length() { return (float) Math.sqrt(length()); }
    public float lengthSqrd() { return s*s + i*i + j*j + k*k; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Transformers ">
    public Quaternion normalize() {
        float l = length();
        return new Quaternion(s / l, i / l, j / l, k / l);
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