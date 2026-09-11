public class Linha2D {
    private Ponto2D[] pontos;
    private Matrix2D matTransform;

    public Linha2D(Ponto2D p0, Ponto2D p1) {
        this.pontos = new Ponto2D[2];
        this.pontos[0] = p0;
        this.pontos[1] = p1;

        this.matTransform = new Matrix2D(0, 0);
    }

    public Ponto2D[] getPontos() {
        return this.pontos;
    }

    public void transform2d() {
        int[] tResult = new int[2];
        for(Ponto2D p : this.pontos) {
            tResult = this.matTransform.applyTransformation();
            p.setpX(tResult[0]);
            p.setpY(tResult[1]);
        }
    }

    // Aplicação de translação no ponto
    public void translate2d(float a, float b) {
        this.matTransform.translate(a, b);
    }

    // Aplicação de rotação no ponto
    public void rotation2d(double ang) {
        this.matTransform.rotation(ang);
    }

    // Aplicação da escala no ponto
    public void scale2d(float a, float b) {
        this.matTransform.scale(a, b);
    }

    // Aplicação de shear no ponto
    public void shear2d(float a, float b) {
        this.matTransform.shear(a, b);
    }
}
