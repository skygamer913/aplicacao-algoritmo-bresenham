package Code3D;
public class Ponto3D {
    private int pX;
    private int pY;
    private int pZ;

    // Construtor
    public Ponto3D(int x, int y, int z) {
        this.pX = x;
        this.pY = y;
        this.pZ = z;
    }

    // Getters
    public int getpX() {
        return pX;
    }

    public int getpY() {
        return pY;
    }
    
    public int getpZ() {
    	return pZ;
    }

    // Setters
    public void setpX(int pX) {
        this.pX = pX;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }
    
    public void setpZ(int pZ) {
    	this.pZ = pZ;
    }
    
    // Função de transformação 2D por matriz
    public void transform3D(Matrix3D mat) {
    	mat.changePoint(this.pX, this.pY, this.pZ);  // Altera as coordenadas do ponto na matriz
    	
    	int[] matT = mat.applyTransformation();  // Gera uma matriz do resultado da transformação
    	
    	this.pX = matT[0];
    	this.pY = matT[1];
    	this.pZ = matT[2];
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
