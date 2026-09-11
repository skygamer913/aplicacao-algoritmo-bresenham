import java.lang.Math;

public class Matrix2D {
    // atributos
    private float[] matFinal;
    private float[] matPonto;
    private boolean isTransformed;

    // Construtor
    public Matrix2D(int x, int y) {        
        this.matPonto = new float[3];
        this.matFinal = new float[9];
        this.isTransformed = false;

        // Valores inicias da matriz de transformação
        for(int i = 0; i < 9; i++) {
            this.matFinal[i] = 0;
        }

        // Valores iniciais das matrizes
        this.matPonto[0] = x;
        this.matPonto[1] = y;
        this.matPonto[2] = 1;
    }

    // Método para multiplicar duas matrizes 3x3
    private void updateMatrix(float[] matA, float[] matB) {
        float[] result = new float[9];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    result[i*3+j] += matA[i*3+k] * matB[k*3+j];
                }
            }
        }

        this.matFinal = result;  // Atualiza a matriz de transformação
    }

    public void resetMatrix() {
        for(int i = 0; i < 9; i++) {
            this.matFinal[i] = 0;
        }
    }

    public void changePoint(int new_x, int new_y) {
        this.matPonto[0] = new_x;
        this.matPonto[1] = new_y;
    }

    // Método para aplicar a transformação ao ponto
    public int[] applyTransformation() {
        // Multiplicação da matriz de transformação pelo ponto
        int[] result = new int[2];
        float[] matAux = new float[3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                matAux[i] += this.matFinal[i*3+j] * this.matPonto[j];
            }
        }
        result[0] = Math.round(matAux[0]);
        result[1] = Math.round(matAux[1]);

        return result;  // Retorna o ponto transformado
    } 

    // Método para aplicar a translação
    public void translate(float a, float b) {
        // Gerando a matriz translate
        float[] matTranslate = {
            1, 0, a,
            0, 1, b,
            0, 0, 1,
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
        float[] matRotation = {
            (float) cosAng, (float) sinAng, 0,
            (float) sinAng * (-1), (float) cosAng, 0,
            0, 0, 1
        };

        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matFinal = matRotation;  // A matriz final recebe a matriz de rotação
            this.isTransformed = true;  // Marca que a transformação foi aplicada

            return;  // Sai do método após aplicar a primeira transformação
        }

        // Multiplicação das matrizes
        updateMatrix(matFinal, matRotation);
    }

    // Método de operção de escala
    public void scale(float a, float b) {
        // Gerando a matriz de escala
        float[] matScale = {
            a, 0, 0,
            0, b, 0,
            0, 0, 1
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

    // Método de shear
    public void shear(float a, float b) {
        // Gerando a matriz de shear
        float[] matShear = {
            1, a, 0,
            b, 1, 0,
            0, 0, 1
        };

        // Verifica se já ocorreu alguma operação de transformação
        if (!isTransformed) {
            this.matFinal = matShear;  // A matriz final recebe a matriz de Shear
            this.isTransformed = true;  // Marca que obteve uma transformação

            return;  // Retorna o método
        }

        // Multiplicação para a matriz de transformação final
        updateMatrix(matShear, matFinal);
    }

}