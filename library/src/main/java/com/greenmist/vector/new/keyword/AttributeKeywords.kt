package com.greenmist.vector.new.keyword

/**
 * Created by geoffpowell on 11/19/17.
 */
enum class AttributeKeywords {
    CLASS,
    CLIP,
    CLIP_PATH,
    CLIPPATHUNITS,
    CLIP_RULE,
    COLOR,
    CX, CY,
    DIRECTION,
    DX, DY,
    FX, FY,
    D,
    DISPLAY,
    FILL,
    FILL_RULE,
    FILL_OPACITY,
    FONT,
    FONT_FAMILY,
    FONT_SIZE,
    FONT_WEIGHT,
    FONT_STYLE,
    // FONT_SIZE_ADJUST, FONT_STRETCH, FONT_VARIANT,
    GRADIENTTRANSFORM,
    GRADIENTUNITS,
    HEIGHT,
    HREF,
    // ID,
    IMAGE_RENDERING,
    MARKER,
    MARKER_START, MARKER_MID, MARKER_END,
    MARKERHEIGHT, MARKERUNITS, MARKERWIDTH,
    MASK,
    MASKCONTENTUNITS, MASKUNITS,
    MEDIA,
    OFFSET,
    OPACITY,
    ORIENT,
    OVERFLOW,
    PATHLENGTH,
    PATTERNCONTENTUNITS, PATTERNTRANSFORM, PATTERNUNITS,
    POINTS,
    PRESERVEASPECTRATIO,
    R,
    REFX,
    REFY,
    REQUIREDFEATURES, REQUIREDEXTENSIONS, REQUIREDFORMATS, REQUIREDFONTS,
    RX, RY,
    SOLID_COLOR, SOLID_OPACITY,
    SPREADMETHOD,
    STARTOFFSET,
    STOP_COLOR, STOP_OPACITY,
    STROKE,
    STROKE_DASHARRAY,
    STROKE_DASHOFFSET,
    STROKE_LINECAP,
    STROKE_LINEJOIN,
    STROKE_MITERLIMIT,
    STROKE_OPACITY,
    STROKE_WIDTH,
    STYLE,
    SYSTEMLANGUAGE,
    TEXT_ANCHOR,
    TEXT_DECORATION,
    TRANSFORM,
    TYPE,
    VECTOR_EFFECT,
    VERSION,
    VIEWBOX,
    WIDTH,
    X, Y,
    X1, Y1,
    X2, Y2,
    VIEWPORT_FILL, VIEWPORT_FILL_OPACITY,
    VISIBILITY,
    UNSUPPORTED;

    companion object {

        private val CACHE: HashMap<String, AttributeKeywords> = HashMap()

        fun fromString(str: String?): AttributeKeywords {
            str ?: return UNSUPPORTED

            var attr = CACHE[str]

            attr?.let {
                return it
            }

            if (str.contains('_')) {
                CACHE.put(str, UNSUPPORTED)
                return UNSUPPORTED
            }
            try {
                attr = valueOf(str.replace('-', '_').toUpperCase())
                CACHE.put(str, attr)
                return attr
            } catch (e: IllegalArgumentException) {
                // Do nothing
            }

            // Unknown attribute name
            CACHE.put(str, UNSUPPORTED)
            return UNSUPPORTED
        }
    }
}