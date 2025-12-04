package Vectors;

public class vec2 {
    
    public float x;
    public float y;
    
    public vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float length() {
        return (float) Math.sqrt(x*x + y*y);
    }
    
    public vec2 rotate(float a) {
        float cs = (float)Math.cos(a), sn = (float)Math.sin(a);
        return new vec2(x * cs - y * sn, x * sn + y * cs);
    }
}
