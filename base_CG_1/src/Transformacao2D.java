import java.lang.Math;

public class Transformacao2D {
    // atributos
    private int[][] matTFinal;
    private int[][] matT;
    private int[] matPonto;
    private boolean isTransformed;

    // Construtor
    public Transformacao2D(int x, int y) {        
        this.matPonto = new int[3];
        this.matTFinal = new int[3][3];
        this.matT = new int[3][3];
        this.isTransformed = false;

        // Valores inicias da matriz de transformação
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.matTFinal[i][j] = 0;
                this.matT[i][j] = 0;
            }
        }

        // Valores iniciais das matrizes
        this.matPonto[0] = x;
        this.matPonto[1] = y;
        this.matPonto[2] = 1;
    }

    // Método para multiplicar duas matrizes 3x3
    private void multMatrices(int[][] matA, int[][] matB) {
        int[][] result = new int[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    result[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }

        this.matTFinal = result;  // Atualiza a matriz de transformação
    }

    // Função de reiniciar a matriz
    private void resetMatrixT() {
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 3; j++) {
                this.matT[i][j] = 0; 
            }
        }
    }

    // Método para aplicar a transformação ao ponto
    public Ponto2D applyTransformation() {
        // Multiplicação da matriz de transformação pelo ponto
        int[] matAux = new int[3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                matAux[i] += this.matTFinal[i][j] * this.matPonto[j];
            }
        }

        return new Ponto2D(matAux[0], matAux[1]);  // Retorna o ponto transformado
    } 

    // Método para aplicar a translação
    public void translate(int a, int b) {
        // Gerando a matriz translate
        for(int i = 0; i < 3; i++) {
            this.matT[i][i] = 1;
        }
        this.matT[0][2] = a;
        this.matT[1][2] = b;
        
        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matTFinal = matT;  // A matriz final recebe a matriz de translação

            this.isTransformed = true;  // Marca que a transformação foi aplicada
            return;  // Sai do método após aplicar a primeira transformação
        } else {
            // Multiplicação das matrizes
            multMatrices(matTFinal, matT);
        }

        resetMatrixT(); // Reseta a matriz
    }

    // Método de operação de rotação
    public void rotation(double ang) {
        // Gerando a matriz de rotação
        this.matT[0][0] = (int)(Math.cos(ang));
        this.matT[0][1] = (int)(Math.sin(ang));

        this.matT[1][1] = matT[0][0];
        this.matT[1][0] = matT[0][1] * -1;

        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matTFinal = matT;  // A matriz final recebe a matriz de translação

            this.isTransformed = true;  // Marca que a transformação foi aplicada
            return;  // Sai do método após aplicar a primeira transformação
        } else {
            // Multiplicação das matrizes
            multMatrices(matTFinal, matT);
        }

        resetMatrixT(); // Reseta a matriz
    }

    // Método de operção de escala
    public void scale(int a, int b) {
        // Gerando a matriz de escala
        this.matT[0][0] = a;
        this.matT[1][1] = b;
        this.matT[2][2] = 1; 
        
        // Verifica se já ocorreu alguma operação de transformação antes
        if(!isTransformed) {
            this.matTFinal = matT;  // A matriz final recebe a matriz de translação

            this.isTransformed = true;  // Marca que a transformação foi aplicada
        } else {
            // Multiplicação das matrizes
            multMatrices(matTFinal, matT);
        }

        resetMatrixT();
    }

}