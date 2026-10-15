package Code3D;

public class Matrix3D {
    // Atributes
    private float[][] matrix;
    private float[] result;

    // Constructor
    public Matrix3D() {
        this.matrix = new float[4][4];
        this.result = new float[4];
        identity();
    }

    // Zera a matriz
    public void reset() {
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void identity() {
        reset();

        matrix[0][0] = 1;
        matrix[1][1] = 1;
        matrix[2][2] = 1;
        matrix[3][3] = 1;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(float mat[][]) {
        for(int i = 0; i < 4;  i++) {
            for(int j = 0; j < 4; j++) {
                matrix[i][j] = mat[i][j];
            }
        }
    }

    // Aplica a transformação da matriz
    public float[] applyTransform(int x, int y, int z, int w) {
        // Cálculo de aplicação da transformação no ponto
        for(int i = 0; i < 4; i++) {
            result[i] = matrix[i][0] * x + matrix[i][1] * y + matrix[i][2] * z + matrix[i][3] * w;
        }

        return result;  // [x, y, z, w]
    }


    public void multiplication(float matrixA[][], float matrixB[][]) {
        int linhasA = matrixA.length;
        int colunasA = matrixA[0].length;
        int linhasB = matrixB.length;
        int colunasB = matrixB[0].length;

        if (colunasA != linhasB) {
            throw new IllegalArgumentException("As matrizes não podem ser multiplicadas.");
        }

        float matrixS[][] = new float[linhasA][colunasB];

        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    matrixS[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        this.matrix = matrixS;
    }

    public Ponto3D multPontos(Ponto3D p) {
        for(int i = 0; i < 4; i++) {
            result[i] = matrix[i][0] * p.getpX() + matrix[i][1] * p.getpY() + matrix[i][2] * p.getpZ() + matrix[i][3] * p.getW();
        }

        float w = (result[3] == 0) ? 1.0f : result[3];

        float xNorm = result[0] / w;
        float yNorm = result[1] / w;
        float zNorm = result[2] / w;

        int x1 = (int) (xNorm + 320);
        int y1 = (int) (240 - yNorm);
        int z1 = (int) zNorm;
        int w1 = 1;

        return new Ponto3D(x1, y1, z1, w1);
    }

    // Translação
    public void translate(float tx, float ty, float tz) {
        reset();

        matrix[0][0] = 1;
        matrix[0][3] = tx;

        matrix[1][1] = 1;
        matrix[1][3] = ty;

        matrix[2][2] = 1;
        matrix[2][3] = tz;

        matrix[3][3] = 1;
    }

    // Escala
    public void scale(float ax, float by, float cz) {
        reset();

        matrix[0][0] = ax;

        matrix[1][1] = by;

        matrix[2][2] = cz;

        matrix[3][3] = 1;
    }

    // Rotação pelo eixo Y
    public void rotateY(float ang) {
        reset();

        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);

        matrix[0][0] = cos;
        matrix[0][2] = -sin;

        matrix[1][1] = 1;

        matrix[2][0] = sin;
        matrix[2][2] = cos;

        matrix[3][3] = 1;
    }

    public void rotateAnyAxis(float ang, float x0, float y0) {
        reset();

        float rad = ang*0.017453f;
        float sin = (float)Math.sin(rad);
        float cos = (float)Math.cos(rad);

        matrix[0][0] = cos;
        matrix[0][1] = -sin;
        matrix[0][2] = x0*(1-cos)+y0*sin;

        matrix[1][0] = sin;
        matrix[1][1] = cos;
        matrix[1][2] = y0*(1-cos)-x0*sin;

        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }

    public void setOblique(float alpha, float ang) {
        reset();

        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);

        matrix[0][0] = 1;
        matrix[0][2] = alpha * cos;

        matrix[1][1] = 1;
        matrix[1][2] = alpha * sin;

        matrix[3][3] = 1;
    }

    public void setCavalier(float ang) {
        setOblique(1.0f, ang);
    }

    public void setCabinet(float ang) {
        setOblique(0.5f, ang);
    }
}
