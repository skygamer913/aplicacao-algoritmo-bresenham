public class Linha2D {
    private Ponto2D[] pontos;
    
    public Linha2D(int x0, int y0, int x1, int y1) {
    	this.pontos = new Ponto2D[2];
    	this.pontos[0] = new Ponto2D(x0, y0);
    	this.pontos[1] = new Ponto2D(x1, y1);
    }
    
    public Linha2D(Ponto2D p0, Ponto2D p1) {
        this.pontos = new Ponto2D[2];
        this.pontos[0] = p0;
        this.pontos[1] = p1;
    }
    
    // Getter
    public Ponto2D getp0() {
        return this.pontos[0];
    }
    
    public Ponto2D getp1() {
    	return this.pontos[1];
    }
    
    // Setter
    public void setPontos(Ponto2D p0, Ponto2D p1) {
    	this.pontos[0] = p0;
    	this.pontos[1] = p1;
    }

    // Método de tranformação 2D da linha
    public void transform2D(Matrix2D matTransform) {
        for(Ponto2D p : pontos) {
        	p.transform2D(matTransform);
        }
    }
}
