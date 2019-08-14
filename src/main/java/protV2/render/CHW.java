package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHW {
    private CHW pDevice;
    private CHW pContext;
    private CHW m_pSwapChain;

    private int pBaseRT;

    private int  pBaseZB;
    private int  pPP;
    private int  pFB;

//    SDL_Window* m_hWnd;
//    HDC m_hDC;
//    SDL_GLContext m_hRC;
}
