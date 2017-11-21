package com.greenmist.vector.svgkit.svg.filters

import android.graphics.RectF
import com.greenmist.vector.svgkit.svg.GraphicsSvg
import com.greenmist.vector.svgkit.svg.parser.FilterOp

/**
 * Created by geoffpowell on 11/18/17.
 */
interface FilterType {

    fun handle(fop: FilterOp, canvas: GraphicsSvg, stroke: Boolean, bounds: RectF)
}