package com.greenmist.vector.svgkit.svg.filters

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.svgkit.svg.GraphicsSvg
import com.greenmist.vector.svgkit.svg.parser.FilterOp

/**
 * Created by geoffpowell on 11/18/17.
 */
class FeBlend() : FilterType {

    override fun handle(fop: FilterOp, canvas: GraphicsSvg, stroke: Boolean, bounds: RectF) {
        // '', 'normal', 'multiply', 'screen', 'darken', 'lighten'
        //TODO May not need this empty check if mode's default value = "normal"
        if (fop.mode.isEmpty()) {
            fop.mode = "normal"
        }

        when {
            fop.mode == "normal" -> canvas.composite = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
            fop.mode == "multiply" -> canvas.composite = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
            fop.mode == "screen" -> canvas.composite = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
            fop.mode == "lighten" -> canvas.composite = PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)
            fop.mode == "darken" -> canvas.composite = PorterDuffXfermode(PorterDuff.Mode.DARKEN)
            else -> SvgLogger.w("feBlend: unknown mode `${fop.mode}`")
        }
    }
}