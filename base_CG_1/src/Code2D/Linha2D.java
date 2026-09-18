package Code2D;

public class Linha2D {
    private Ponto2D p0;
    private Ponto2D p1;
    
    public Linha2D(int x0, int y0, int x1, int y1) {
    	this.p0 = new Ponto2D(x0, y0);
    	this.p1 = new Ponto2D(x1, y1);
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

    public Ponto2D findIntersection(int width, int height) {
    	
    	
    }
}
