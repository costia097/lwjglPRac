package prot;

class DrawElementContext {
    private int vao;
    private int countOfVertex;
    private int mode;
    private int shaderProgram;
    private Fvector position;
    private boolean isIndexed;
    private RenderElementType renderElementType;
    private Fvector color;

    DrawElementContext(int vao, int countOfVertex, int mode, boolean isIndexed, RenderElementType renderElementType) {
        this.vao = vao;
        this.countOfVertex = countOfVertex;
        this.mode = mode;
        this.isIndexed = isIndexed;
        this.renderElementType = renderElementType;
    }

    int getVao() {
        return vao;
    }

    int getCountOfVertex() {
        return countOfVertex;
    }

    int getMode() {
        return mode;
    }

    int getShaderProgram() {
        return shaderProgram;
    }

    Fvector getPosition() {
        return position;
    }

    boolean isIndexed() {
        return isIndexed;
    }

    RenderElementType getRenderElementType() {
        return renderElementType;
    }

    void setShaderProgram(int shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    void setPosition(Fvector position) {
        this.position = position;
    }

    Fvector getColor() {
        return color;
    }

    void setColor(Fvector color) {
        this.color = color;
    }
}
