package Code2D;

public class Matrix2D {
	// Atributos
	private float[][] matrix;
	private float[] result;
	
	// Construtor
	public Matrix2D() {
		this.matrix = new float[3][3];
		this.result = new float[3];
	}
	
	// Getters
	public float[][] getMatrix() {
		return matrix;
	}
	
	// Reseta a matriz
	public void reset() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	
	// Aplicação da transformação
	public float[] applyTransform(int x, int y, int w) {
		result[0] = x;
		result[1] = y;
		result[2] = w;
		
		for(int i = 0; i < 3; i++) {
			result[i] = matrix[i][0] * x + matrix[i][1] * y + matrix[i][2] * w;
		}
		
		return result;  // [x, y, w]
	}
	
	public void translate(float tx, float ty) {
		reset();
		
		matrix[0][0] = 1;
		matrix[0][2] = tx;
		
		matrix[1][1] = 1;
		matrix[1][2] = ty;
		
		matrix[2][2] = 1;
	}
	
	public void scale(float ax, float bx) {
		reset();
		
		matrix[0][0] = ax;
		
		matrix[1][1] = bx;
		
		matrix[2][2] = 1;
	}
	
	public void rotate(float ang) {
		reset();
		
		float cos = (float) Math.cos(ang);
		float sin = (float) Math.sin(ang);
		
		matrix[0][0] = cos;
		matrix[0][1] = sin;
		
		matrix[1][0] = -sin;
		matrix[1][1] = cos;
		
		matrix[2][2] = 1;
	}
	
}
