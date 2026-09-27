package Code2D;

public class Linha2D {
    private Ponto2D p0;
    private Ponto2D p1;
    private LinhaClip cohenC;
    
    public Linha2D(int x0, int y0, int x1, int y1) {
    	this.p0 = new Ponto2D(x0, y0);
    	this.p1 = new Ponto2D(x1, y1);
    	this.cohenC = new LinhaClip();
    }
    
    public Linha2D(Ponto2D p0, Ponto2D p1) {
        this.p0 = p0;
        this.p1 = p1;
    }
    
    // Getter
    public Ponto2D getp0() {
        return this.p0;
    }
    
    public Ponto2D getp1() {
    	return this.p1;
    }
    
    // Setter
    public void setPontos(Ponto2D p0, Ponto2D p1) {
    	this.p0 = p0;
    	this.p1 = p1;
    }
    
    public boolean isOnScreen(int w, int h) {
    	cohenC.resetClip();
    	cohenC.applyClipping(p0, p1, w, h);
    	
    	byte codeC = cohenC.getCodeC();
    	
    	return (codeC == 0x00);
    }
    
    public int[] findIntersection(int w, int h) {    	
    	cohenC.resetClip();
    	cohenC.applyClipping(p0, p1, w, h);
    	
    	byte codeC = cohenC.getCodeC();
    	byte aux;
    	int[] np = new int[4];  // [x0, y0, x1, y0]
    	
    	// Procura interseção no p0
    	aux = (byte) (codeC & 0x0f);
    	
    	if(aux == (byte) 0x00) {
    		np[0] = p0.getpX();
    		np[1] = p0.getpY();
    	} else {
    		
    	}
    	
    	// Procura interseção no p1
    	aux = (byte) (codeC & 0xf0);
    	
    	if(aux == (byte) 0x00) {
    		np[2] = p1.getpX();
    		np[3] = p1.getpY();
    	} else {
    		
    	}
    	
    	return np;
    }
    
    public void translate(float tx, float ty) {
    	Matrix2D mat = new Matrix2D();
    	
    	mat.translate(tx, ty);
    	
    	p0.transform2D(mat);
    	p1.transform2D(mat);
    }
    
    public void scale(float ax, float bx) {
    	Matrix2D mat = new Matrix2D();
    	
    	mat.scale(ax, bx);
    	
    	p0.transform2D(mat);
    	p1.transform2D(mat);
    }
    
    public void rotate(float ang) {
    	Matrix2D mat = new Matrix2D();
    	
    	mat.rotate(ang);
    	
    	p0.transform2D(mat);
    	p1.transform2D(mat);
    }

}
