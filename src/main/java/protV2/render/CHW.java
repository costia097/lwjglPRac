package protV2.render;

import lombok.Getter;
import lombok.Setter;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_RENDERER;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.GL_VENDOR;
import static org.lwjgl.opengl.GL11C.glColorMask;
import static org.lwjgl.opengl.GL11C.glGetString;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20C.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20C.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30C.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30C.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30C.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL41.glBindProgramPipeline;
import static org.lwjgl.opengl.GL41.glGenProgramPipelines;
import static org.lwjgl.opengl.GL41C.glDeleteProgramPipelines;
import static org.lwjgl.opengl.GL42.glTexStorage2D;

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

    private long windowPointer;

    private int[] psCurrentVidMode = {1024, 768};

    public CHW() {
        pDevice = this;
        pContext = this;
        m_pSwapChain = this;
        pBaseRT = 0;
        pBaseZB = 0;
        pPP = 0;
        pFB = 0;
        windowPointer = 0;
    }

    public void CreateDevice() {
        // Initialize OpenGL Extension Wrangler
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Could not initialize glew.");
        }


        int[] iMaxVTFUnits = new int[1];
        int[] iMaxCTIUnits = new int[1];

        glGetIntegerv(GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, iMaxVTFUnits);
        glGetIntegerv(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, iMaxCTIUnits);

        System.out.println("* GPU vendor:" + glGetString(GL_VENDOR) + " device:" + glGetString(GL_RENDERER));
        System.out.println("* GPU OpenGL version:" + glGetString(GL_VERSION));
        System.out.println("* GPU OpenGL shading language version:" + glGetString(GL_SHADING_LANGUAGE_VERSION));
        System.out.println("* GPU OpenGL VTF units: " + iMaxVTFUnits[0] + "CTI units: " + iMaxCTIUnits[0]);

        //	Create render target and depth-stencil views here
        UpdateViews();
    }

    public void UpdateViews() {
        // Create the program pipeline used for rendering with shaders
        pPP = glGenProgramPipelines();

        glBindProgramPipeline(pPP);
        checkGL();

        // Create the default framebuffer
        pFB = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, pFB);
        checkGL();

        // Create a color render target
        pBaseRT = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, pBaseRT);
        checkGL();
        glTexStorage2D(GL_TEXTURE_2D, 1, GL_RGBA8, psCurrentVidMode[0], psCurrentVidMode[1]);
        checkGL();

        // Create depth/stencil buffer
        pBaseZB = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, pBaseZB);
        checkGL();
        glTexStorage2D(GL_TEXTURE_2D, 1, GL_DEPTH24_STENCIL8, psCurrentVidMode[0], psCurrentVidMode[1]);
        checkGL();
    }

    public void DestroyDevice() {
        glfwTerminate();
    }

    public void Reset() {
        glDeleteProgramPipelines(pPP);
        checkGL();

        glDeleteFramebuffers(pFB);
        checkGL();

        glDeleteTextures(pBaseRT);
        checkGL();

        glDeleteTextures(pBaseZB);
        checkGL();

        UpdateViews();
    }

    public void ClearRenderTargetView(int pRenderTargetView, float[] ColorRGBA) {
        if (pRenderTargetView == 0){
            return;
        }

        // Attach the render target
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, pRenderTargetView, 0);
        checkGL();

        // Clear the color buffer without affecting the global state
        glColorMask(true,true,true,true);
        glClearColor(ColorRGBA[0], ColorRGBA[1], ColorRGBA[2], ColorRGBA[3]);
        glClear(GL_COLOR_BUFFER_BIT);
        checkGL();
    }

    public void ClearDepthStencilView(int pDepthStencilView, int ClearFlags, float Depth, int Stencil) {
        if (pDepthStencilView == 0){
            return;
        }

        // Attach the depth buffer
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, pDepthStencilView, 0);
        checkGL();

        //todo
        int mask = 0;
//        if (ClearFlags & D3D_CLEAR_DEPTH)
//            mask |= (u32)GL_DEPTH_BUFFER_BIT;
//        if (ClearFlags & D3D_CLEAR_STENCIL)
//            mask |= (u32)GL_STENCIL_BUFFER_BIT;

//        if (ClearFlags & D3D_CLEAR_DEPTH)
//        {
//            glDepthMask(GL_TRUE);
//            glClearDepthf(Depth);
//        }
//        if (ClearFlags & D3D_CLEAR_STENCIL)
//        {
//            glStencilMask(~0);
//            glClearStencil(Stencil);
//        }

        glClear(mask);
        checkGL();
    }

    //todo
    public boolean Present(int SyncInterval, int Flags) {
        return false;
    }

    public static void checkGL() {
        int res = glGetError();
        if (res != GL_NO_ERROR) {
            throw new IllegalStateException("Error.");
        }
    }

}
