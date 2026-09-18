import java.awt.Graphics;

public class Triangulo3D {
    // Atributos
	private Ponto3D[] pontos;
    
	// Construtor
    public Triangulo3D(Ponto3D p0, Ponto3D p1, Ponto3D p2) {
        this.pontos = new Ponto3D[3];
        
        this.pontos[0] = p0;
        this.pontos[1] = p1;
        this.pontos[2] = p2;
    }
    
    // Getter
    public Ponto3D getp0() {
        return this.pontos[0];
    }
    
    public Ponto3D getp1() {
    	return this.pontos[1];
    }
    
    public Ponto3D getp2() {
    	return this.pontos[2];
    }
    
    // Setter
    public void setPontos(Ponto3D p0, Ponto3D p1, Ponto3D p2) {
    	this.pontos[0] = p0;
    	this.pontos[1] = p1;
    	this.pontos[2] = p2;
    }
    
    public void draw(Graphics g) {
    	g.drawLine(pontos[0].getpZ(), pontos[0].getpY(), pontos[1].getpZ(), pontos[1].getpY());
    	g.drawLine(pontos[0].getpZ(), pontos[0].getpY(), pontos[2].getpZ(), pontos[2].getpY());
    	g.drawLine(pontos[1].getpZ(), pontos[1].getpY(), pontos[2].getpZ(), pontos[2].getpY());
    }
    
    // Método de tranformação 3D da linha
    public void transform3D(Matrix3D matTransform) {
        for(Ponto3D p : pontos) {
        	p.transform3D(matTransform);
        }
    }

    public boolean isCompletelyInside(int width, int height) {
        return pontos[0].isValidCoordenates(width, height) && pontos[1].isValidCoordenates(width, height);
    }

    public boolean isPartiallyInside(int width, int height) {
        return pontos[0].isValidCoordenates(width, height) || pontos[1].isValidCoordenates(width, height);
    }
}
