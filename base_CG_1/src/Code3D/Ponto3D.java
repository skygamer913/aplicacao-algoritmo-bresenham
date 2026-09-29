package Code3D;

public class Ponto3D {
    private float pX;
    private float pY;
    private float pZ;
    private float w;

    // Construtor
    public Ponto3D(float x, float y, float z) {
        this.pX = x;
        this.pY = y;
        this.pZ = z;
        this.w = 1;
    }

    public Ponto3D(float x, float y, float z, float w) {
        this.pX = x;
        this.pY = y;
        this.pZ = z;
        this.w = w;
    }

    // Getters
    public float getpX() {
        return pX;
    }

    public float getpY() {
        return pY;
    }

    public float getpZ() {
        return pZ;
    }

    public float getW() {
        return w;
    }

    public void setX(float x) {
        this.pX = x;
    }

    public void setY(float y) {
        this.pY = y;
    }

    public void setZ(float z) {
        this.pZ = z;
    }

    public void setW(float w) {
        this.w = w;
    }

    public void transform3D(Matrix3D mat) {
        float[] result = mat.applyTransform((int)pX, (int)pY, (int)pZ, (int)w);

        pX = Math.round(result[0]);
        pY = Math.round(result[1]);
        pZ = Math.round(result[2]);
        w = Math.round(result[3]);
    }
}
