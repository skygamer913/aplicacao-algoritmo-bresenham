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

    public int posCalculation(int width, int channel) {
        return this.pY * (width * channel) + this.pX * channel;
    }

    public boolean isValidCoordenates(int width, int height) {
        return (this.pX >= 0 && this.pY >= 0) && (this.pX <= width && this.pY <= height);
    }
}
