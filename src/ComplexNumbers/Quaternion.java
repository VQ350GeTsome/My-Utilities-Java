package ComplexNumbers;

import Vectors.vec3;

public class Quaternion {
    
    public float s, i, j, k;

    public Quaternion() { s = 1; i = 0; j = 0; k = 0; }
    
    public Quaternion(float w, float x, float y, float z) {
        this.s = w; this.i = x; this.j = y; this.k = z;
    }

    public Quaternion normalize() {
        float l = length();
        return new Quaternion(s / l, i / l, j / l, k / l);
    }
    
    public float length() { return (float)Math.sqrt(s*s + i*i + j*j + k*k); }

    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
            s*q.s - i*q.i - j*q.j - k*q.k,
            s*q.i + i*q.s + j*q.k - k*q.j,
            s*q.j - i*q.k + j*q.s + k*q.i,
            s*q.k + i*q.j - j*q.i + k*q.s
        );
    }
    public Quaternion scaleImag(float f) { return new Quaternion(s, i*f, j*f, k*f); }

    public Quaternion conjugate() { return new Quaternion(s, -i, -j, -k); }

    public vec3 rotate(vec3 v) {
        if (s == 1 && length() == 1) return v;  //If no rotation just return v.
        Quaternion qv = new Quaternion(0, v.x, v.y, v.z);
        Quaternion res = this.multiply(qv).multiply(this.conjugate());
        return new vec3(res.i, res.j, res.k);
    }
    
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
}