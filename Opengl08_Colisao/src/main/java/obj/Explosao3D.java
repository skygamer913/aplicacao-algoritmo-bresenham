package obj;

import Model.Model;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.ShaderProgram;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import main.Main3D;

public class Explosao3D extends Object3D {
    public Vector3f cor = new Vector3f();
    public Model model = null;
    FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
    public float scaleVel = 0;
    public long timer = 0;

    public Explosao3D(float x, float y, float z, float r, float scale) {
        super(x, y, z);
        raio = r;
        scaleVel = scale;
        model = Main3D.esfera;
    }

    @Override
    public void DesenhaSe(ShaderProgram shader) {
        super.DesenhaSe(shader);

        Matrix4f modelm = new Matrix4f();
        modelm.setIdentity();

        //System.out.println(""+x+" "+y+" "+z);
        modelm.translate(new Vector3f(x,y,z));
        modelm.scale(new Vector3f(raio,raio,raio));

        int modellocation = glGetUniformLocation(shader.programID, "model");

        modelm.storeTranspose(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4fv(modellocation, false, matrixBuffer);

        model.draw();
    }

    @Override
    public void SimulaSe(long diftime) {
        super.SimulaSe(diftime);
        timer += diftime;

        x += vx*diftime/1000.0f;
        y += vy*diftime/1000.0f;
        z += vz*diftime/1000.0f;

        raio += scaleVel*diftime/1000.0f;

        if(timer > 5000) {
            vivo = false;
        }
    }
}
