package Code3D;

public class Matrix3D {
    // atributos
    private float[] matFinal;
    private float[] matPonto;
    private boolean isTransformed;

    // Construtor
    public Matrix3D(int x, int y, int z) {        
        this.matPonto = new float[4];
        this.matFinal = new float[16];
        this.isTransformed = false;

        // Valores inicias da matriz de transformação
        for(int i = 0; i < 16; i++) {
            this.matFinal[i] = 0;
        }

        // Valores iniciais das matrizes
        this.matPonto[0] = x;
        this.matPonto[1] = y;
        this.matPonto[2] = z;
        this.matPonto[4] = 1;
    }

    public boolean isTransformed() {
    	return this.isTransformed;
    }
    
    // Método para multiplicar duas matrizes 3x3
    private void updateMatrix(float[] matA, float[] matB) {
        float[] result = new float[16];

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int k = 0; k < 4; k++) {
                    result[i*4+j] += matA[i*4+k] * matB[k*4+j];
                }
            }
        }

        this.matFinal = result;  // Atualiza a matriz de transformação
    }

    public void resetMatrix() {
        for(int i = 0; i < 16; i++) {
            this.matFinal[i] = 0;
        }
        this.isTransformed = false;
    }

    public void changePoint(int new_x, int new_y, int new_z) {
        this.matPonto[0] = new_x;
        this.matPonto[1] = new_y;
        this.matPonto[2] = new_z;
    }

    // Método para aplicar a transformação ao ponto
    public int[] applyTransformation() {
        // Multiplicação da matriz de transformação pelo ponto
        int[] result = new int[3];
        float[] matAux = new float[4];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                matAux[i] += this.matFinal[i*4+j] * this.matPonto[j];
            }
        }
        result[0] = Math.round(matAux[0]);
        result[1] = Math.round(matAux[1]);
        result[2] = Math.round(matAux[2]);

        return result;  // Retorna o ponto transformado
    } 

    // Método para aplicar a translação
    public void translate(float tx, float ty, float tz) {
        // Gerando a matriz translate
        float[] matTranslate = {
            1, 0, 0, tx,
            0, 1, 0, ty,
            0, 0, 1, tz,
            0, 0, 0, 1
        };
        
        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matFinal = matTranslate;  // A matriz final recebe a matriz de translação

            this.isTransformed = true;  // Marca que a transformação foi aplicada
            return;  // Sai do método após aplicar a primeira transformação
        }
        // Multiplicação das matrizes
        updateMatrix(matFinal, matTranslate);
    }

    // Método de operação de rotação
    public void rotation(double ang) {
        // Gerando a matriz de rotação
        float cosAng = (float)(Math.cos(ang));
        float sinAng = (float)(Math.sin(ang));
        
        // Matriz de rotação
        float[] matRotationZ = {
            (float) cosAng, (float) sinAng, 0, 0,
            (float) -sinAng, (float) cosAng, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        };

        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matFinal = matRotationZ;  // A matriz final recebe a matriz de rotação
            this.isTransformed = true;  // Marca que a transformação foi aplicada

            return;  // Sai do método após aplicar a primeira transformação
        }

        // Multiplicação das matrizes
        updateMatrix(matFinal, matRotationZ);
    }

    // Método de operção de escala
    public void scale(float ax, float by, float cz) {
        // Gerando a matriz de escala
        float[] matScale = {
            ax, 0, 0, 0,
            0, by, 0, 0,
            0, 0, cz, 0,
            0, 0, 0, 1
        };
        
        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matFinal = matScale;  // A matriz final recebe a matriz de escala
            this.isTransformed = true;  // Marca que a transformação foi aplicada

            return;  // Retorna a operação
        }
        // Multiplicação das matrizes
        updateMatrix(matFinal, matScale);
    }
}
