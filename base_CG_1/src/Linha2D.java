public class Linha2D {
    private Ponto2D[] pontos;

    public Linha2D(Ponto2D p0, Ponto2D p1) {
        this.pontos = new Ponto2D[2];
        this.pontos[0] = p0;
        this.pontos[1] = p1;
    }
    
    // Getter
    public Ponto2D[] getPontos() {
        return this.pontos;
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
