package Code3D;
import java.awt.Graphics;

public class Triangulo3D {
    // Atributos
    private Ponto3D p0;
    private Ponto3D p1;
    private Ponto3D p2;

    // Construtor
    public Triangulo3D(Ponto3D p0, Ponto3D p1, Ponto3D p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    // Getter
    public Ponto3D getp0() {
        return this.p0;
    }

    public Ponto3D getp1() {
        return this.p1;
    }

    public Ponto3D getp2() {
        return this.p2;
    }

    // Setter
    public void setPontos(Ponto3D p0, Ponto3D p1, Ponto3D p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    // Desenha o triangulo na tela
    public void draw(Graphics g) {
        g.drawLine((int)p0.getpX(), (int)p0.getpY(), (int)p1.getpX(), (int)p1.getpY());
        g.drawLine((int)p0.getpX(), (int)p0.getpY(), (int)p2.getpX(), (int)p2.getpY());
        g.drawLine((int)p1.getpX(), (int)p1.getpY(), (int)p2.getpX(), (int)p2.getpY());
    }

    public void desenhase(Graphics dbg, Matrix3D modelview, Matrix3D projection) {
        Ponto3D pa1 = modelview.multPontos(p0);
        Ponto3D pb1 = modelview.multPontos(p1);
        Ponto3D pc1 = modelview.multPontos(p2);

        Ponto3D pa2 = projection.multPontos(pa1);
        Ponto3D pb2 = projection.multPontos(pb1);
        Ponto3D pc2 = projection.multPontos(pc1);

        dbg.drawLine((int)pa2.getpX(),(int)pa2.getpY(),(int)pb2.getpX(),(int)pb2.getpY());
        dbg.drawLine((int)pb2.getpX(),(int)pb2.getpY(),(int)pc2.getpX(),(int)pc2.getpY());
        dbg.drawLine((int)pc2.getpX(),(int)pc2.getpY(),(int)pa2.getpX(),(int)pa2.getpY());
    }

    // Translação
    public void translate(float tx, float ty, float tz) {
        Matrix3D mat = new Matrix3D();
        mat.translate(tx, ty, tz);

        // Aplica a transformação em todos os pontos
        p0.transform3D(mat);
        p1.transform3D(mat);
        p2.transform3D(mat);
    }

    // Escala
    public void scale(float ax, float by, float cz) {
        Matrix3D mat = new Matrix3D();
        mat.scale(ax, by, cz);

        p0.transform3D(mat);
        p1.transform3D(mat);
        p2.transform3D(mat);
    }

    // Rotação pelo eixo Y
    public void rotateY(float ang) {
        Matrix3D mat = new Matrix3D();
        mat.rotateY(ang);

        p0.transform3D(mat);
        p1.transform3D(mat);
        p2.transform3D(mat);
    }

    public void rotateAnyAxis(float ang, float x0, float y0) {
        Matrix3D mat = new Matrix3D();
        mat.rotateAnyAxis(ang, x0, y0);

        p0.transform3D(mat);
        p1.transform3D(mat);
        p2.transform3D(mat);
    }
}
