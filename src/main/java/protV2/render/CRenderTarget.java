package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CRenderTarget {

    private int dwWidth;
    private int dwHeight;
    private int dwAccumulatorClearMark;

    private int dwLightMarkerID;

    private IBlender b_occq;
    private IBlender b_accum_mask;
    private IBlender b_accum_direct;
    private IBlender b_accum_point;
    private IBlender b_accum_spot;
    private IBlender b_accum_reflected;
    private IBlender b_bloom;
    private IBlender b_luminance;
    private IBlender b_combine;
    private IBlender b_postprocess_msaa;
    private IBlender b_bloom_msaa;
    private IBlender[] b_combine_msaa;
    private IBlender[] b_accum_mask_msaa;
    private IBlender[] b_accum_spot_msaa;
    private IBlender[] b_accum_direct_msaa;
    private IBlender[] b_accum_direct_volumetric_msaa;
    private IBlender[] b_accum_direct_volumetric_sun_msaa;
    private IBlender[] b_accum_volumetric_msaa;
    private IBlender[] b_accum_point_msaa;
    private IBlender[] b_accum_reflected_msaa;
    private IBlender b_ssao;
    private IBlender[] b_ssao_msaa;
    private IBlender b_fxaa;


}
