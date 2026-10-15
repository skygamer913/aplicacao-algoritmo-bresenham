package Code2D;

public class LinhaClip {
	// Atributes
	private byte codeC;
	
	// Constructor
	public LinhaClip() {
		this.codeC = (byte) 0xff;  // codeC = 1111 1111
	}
	
	public byte getCodeC() {
		return codeC;
	}
	
	public void resetClip() {
		codeC = (byte) 0xff;  // 1111 1111
	}
	
	public boolean isValid() {
		return (codeC == 0xff);
	}
	
	// Gerando o código de clipping de Cohen
	private void p0Clipping(int x, int y, int w, int h) {
		byte aux = (byte) 0xf0;  // 1111 0000
		
		// Gerando o código de Cohen no X
		if(0 <= x && x <= w) {
			aux = (byte) (aux | 0x0c);  // ---- 1100 
			codeC = (byte) (codeC & aux);
		} else {
			// Caso está fora da tela
			if(x < 0) {
				aux = (byte) (aux | 0x0d);  // ---- 1101
				codeC = (byte) (codeC & aux);
			} else {
				aux = (byte) (aux | 0x0e);  // ---- 1110
				codeC = (byte) (codeC & aux);
			}
		}
		
		aux = (byte) 0xf0;
		
		// Gerando o ćodigo de Cohen no Y
		if(0 <= y && y <= h) {
			aux = (byte) (aux | 0x03);  // ---- 0011
			codeC = (byte) (codeC & aux);
		} else {
			
			if(y < 0) {
				aux = (byte) (aux | 0x07);  // ---- 0111
				codeC = (byte) (codeC & aux);
			} else {
				aux = (byte) (aux | 0x0b);  // ---- 1011
				codeC = (byte) (codeC & aux);
			}
		}
		
	}

	private void p1Clipping(int x, int y, int w, int h) {
		byte aux = (byte) 0x0f;  // 0000 1111
		
		// Gerando o código de Cohen no X
		if(0 <= x && x <= w) {
			aux = (byte) (aux | 0xc0);  // 1100 ----
			codeC = (byte) (codeC & aux);
		} else {
			// Caso está fora da tela
			if(x < 0) {
				aux = (byte) (aux | 0xd0);  // 1101 ----
				codeC = (byte) (codeC & aux);
			} else {
				aux = (byte) (aux | 0xe0);  // 1110 ----
				codeC = (byte) (codeC & aux);
			}
		}
		
		aux = (byte) 0x0f;
		
		// Gerando o ćodigo de Cohen no Y
		if(0 <= y && y <= h) {
			aux = (byte) (aux | 0x30);  // 0011 ----
			codeC = (byte) (codeC & aux);
		} else {
			// Caso está fora da tela
			if(y < 0) {
				aux = (byte) (aux | 0x70);  // 0111 ----
				codeC = (byte) (codeC & aux);
			} else {
				aux = (byte) (aux | 0xb0);  // 1011 ----
				codeC = (byte) (codeC & aux);
			}
		}
	}
	
	public void applyClipping(Ponto2D p0, Ponto2D p1, int w, int h) {
		p0Clipping(p0.getpX(), p0.getpY(), w, h);
		p1Clipping(p1.getpX(), p1.getpY(), w, h);
	}
	
}
