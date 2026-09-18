public class Linha3D {
	// Atributos
	private Ponto3D p0;
	private Ponto3D p1;
	
	// Construtor
	public Linha3D(Ponto3D p0, Ponto3D p1) {
		super();
		this.p0 = p0;
		this.p1 = p1;
	}
	
	// Getter p0
	public Ponto3D getP0() {
		return p0;
	}
	
	// Setter p0
	public void setP0(Ponto3D p0) {
		this.p0 = p0;
	}
	
	// Getter p1
	public Ponto3D getP1() {
		return p1;
	}

	// Setter p1
	public void setP1(Ponto3D p1) {
		this.p1 = p1;
	}
	
}
