package Code2D;

public class Ponto2D {
    private int pX;
    private int pY;
    private int w;

    // Construtor
    public Ponto2D(int x, int y) {
        this.pX = x;
        this.pY = y;
        this.w = 1;
    }

    // Getters
    public int getpX() {
        return pX;
    }

    public int getpY() {
        return pY;
    }

    // Setters
    public void updatePoint(int pX, int pY) {
    	this.pX = pX;
    	this.pY = pY;
    }
    
    // Função de transformação 2D por matriz
    public void transform2D(Matrix2D mat) {
    	float[] matT = mat.applyTransform(pX, pY, 1);  // Gera uma matriz do resultado da transformação
    	
    	this.pX = Math.round(matT[0]);
    	this.pY = Math.round(matT[1]);
    }
    
    // Cálculo da posição do pixel
    public int posCalculation(int width, int channel) {
        return this.pY * (width * channel) + this.pX * channel;
    }

    // Validação da coordenada
    public boolean isValidCoordenates(int width, int height) {
        return (this.pX >= 0 && this.pY >= 0) && (this.pX <= width && this.pY <= height);
    }
}
