package protV2.render;

public class R_constant_setup {

    public enum RC {
        RC_1x1, // vector1, or scalar
        RC_1x4, // vector4
        RC_1x3, // vector3
        RC_1x2, // vector2
        RC_2x4, // 4x2 matrix, transpose
        RC_3x4, // 4x3 matrix, transpose
        RC_4x4, // 4x4 matrix, transpose
        RC_1x4a, // array: vector4
        RC_3x4a, // array: 4x3 matrix, transpose
        RC_4x4a // array: 4x4 matrix, transpose
    }

    public enum RC_dest {
        RC_dest_pixel,
        RC_dest_vertex,
        RC_dest_sampler,
        RC_dest_geometry,
        RC_dest_hull,
        RC_dest_domain,
        RC_dest_compute,
        RC_dest_compute_cb_index_mask,
        RC_dest_compute_cb_index_shift,
        RC_dest_domain_cb_index_mask,
        RC_dest_domain_cb_index_shift,
        RC_dest_hull_cb_index_mask,
        RC_dest_hull_cb_index_shift,
        RC_dest_pixel_cb_index_mask,
        RC_dest_pixel_cb_index_shift,
        RC_dest_vertex_cb_index_mask,
        RC_dest_vertex_cb_index_shift ,
        RC_dest_geometry_cb_index_mask,
        RC_dest_geometry_cb_index_shift
    }
}
