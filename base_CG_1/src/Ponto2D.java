public class Ponto2D {
    private int pX;
    private int pY;

    // Construtor
    public Ponto2D(int x, int y) {
        this.pX = x;
        this.pY = y;
    }

    // Getters
    public int getpX() {
        return pX;
    }

    public int getpY() {
        return pY;
    }
    
    // Setters
    public void setpX(int pX) {
        this.pX = pX;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }

    public int positionCalculation(int width, int channel) {
        return this.pY * (width * channel) + this.pX * channel;
    }
    
}
