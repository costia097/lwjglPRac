package protV2.render;

public class CRT {
    private int dwWidth;
    private int dwHeight;

    private D3DFORMAT fmt;
    private int _order;

    private int pRT;
    private int pZRT;
    private int target;

    private void create(String Name, int w, int h, D3DFORMAT f, int SampleCount) {

        //todo


        dwWidth = w;
        dwHeight = h;
        fmt = f;
    }

}
