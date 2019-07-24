package prot;

class BorderBox {
    private Fpoint leftBottomVertex;
    private Fpoint rightTopVertex;

    BorderBox(Fpoint leftBottomVertex, Fpoint rightTopVertex) {
        this.leftBottomVertex = leftBottomVertex;
        this.rightTopVertex = rightTopVertex;
    }

    Fpoint getLeftBottomVertex() {
        return leftBottomVertex;
    }

    Fpoint getRightTopVertex() {
        return rightTopVertex;
    }
}
