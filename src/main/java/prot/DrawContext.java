package prot;

public class DrawContext {
    private int vao;
    private int countOfVertex;
    private int mode;
    private int shaderProgram;
    private Fvector position;

    private boolean isIndexed;

    public DrawContext(int vao, int countOfVertex, int mode, boolean isIndexed) {
        this.vao = vao;
        this.countOfVertex = countOfVertex;
        this.mode = mode;
        this.isIndexed = isIndexed;
    }

    public int getVao() {
        return vao;
    }

    public int getCountOfVertex() {
        return countOfVertex;
    }

    public void setShaderProgram(int shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    public int getMode() {
        return mode;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public Fvector getPosition() {
        return position;
    }

    public void setPosition(Fvector position) {
        this.position = position;
    }
}
