package prot;

class DrawUiUtils {

    static UiButton drawIUButton() {
        float[] borderLineVertexes = new float[]{
                -0.05f, 0.05f, 0,
                0.05f, 0.05f, 0,
                0.05f, -0.05f, 0,
                -0.05f, -0.05f, 0,
                -0.05f, 0.05f, 0
        };
        DrawElementContext drawElementContext = DrawUtils.drawLine(borderLineVertexes, 5);
        UiButton uiButton = new UiButton(drawElementContext.getVao(),
                drawElementContext.getCountOfVertex(),
                drawElementContext.getMode(),
                drawElementContext.isIndexed(),
                new BorderBox(new Fpoint(-0.05f, -0.05f, 0), new Fpoint(0.1f, 0.1f, 0)),
                o -> System.out.println("A")
        );
        uiButton.setColor(new Fvector(1, 1, 0));
        PositionToElementMapper.addElement(uiButton);

        return uiButton;
    }
}
