package com.greenmist.vector.svgkit.svg

import android.graphics.*
import com.greenmist.vector.svgkit.android.ExternalSupport
import com.greenmist.vector.svgkit.svg.paint.LinearGradientPaint
import com.greenmist.vector.svgkit.svg.paint.RadialGradientPaint

/**
 * Created by geoffpowell on 11/18/17.
 */

class GraphicsSvg(val canvas: Canvas) {
    
    val paintStroke: Paint = Paint()
    val paintFill: Paint = Paint()
    val paintText: Paint = Paint()
    var textSize: RectF? = null
    var fontNames: Array<String>? = null
    var storedPaintFillColor: Int = 0
    var storedPaintStrokeColor: Int = 0
    var gradientOpacity: ColorSvg? = null
    var gradient: Shader? = null
    var composite: Xfermode? = null
    
    init {
        paintStroke.isAntiAlias = true
        paintStroke.style = Paint.Style.STROKE

        paintFill.isAntiAlias = true
        paintFill.style = Paint.Style.FILL

        paintText.isAntiAlias = true
        paintText.style = Paint.Style.FILL_AND_STROKE
    }

    constructor(bitmap: Bitmap) : this(Canvas(bitmap))

    fun postTranslate(x: Int, y: Int) {
        canvas.translate(x.toFloat(), y.toFloat())
    }

    fun setFillColor(color: Int) {
        paintFill.color = color
    }

    fun setStrokeColor(color: Int) {
        paintStroke.color = color
    }

    fun setTextColor(color: Int) {
        paintText.color = color
    }
/*
    fun transform(matrix: Matrix?, clipPath: ClipPath?) {
        canvas.save()
        if (matrix != null)
            canvas.concat(matrix)
        if (clipPath != null) {
            canvas.clipPath(clipPath.path)
        }
    }
*/
    fun save() {
        canvas.save()
    }

    fun restore() {
        canvas.restore()
    }

    fun setTransform(transform: Matrix) {
        canvas.matrix = transform
        canvas.save()
    }

    fun setStroke(stroke: BasicStroke) {
        paintStroke.strokeWidth = stroke.lineWidth
        paintStroke.strokeCap = stroke.capStyle
        paintStroke.strokeJoin = stroke.joinStyle
        if (stroke.dashArray != null && stroke.dashOffset != null) {
            paintStroke.pathEffect = DashPathEffect(stroke.dashArray, stroke.dashOffset)
        } else {
            paintStroke.pathEffect = null
        }
    }

    fun fillRect(x1: Int, y1: Int, x2: Int, y2: Int) {
        val paint = Paint()
        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawRect(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    fun stringWidth(text: String): Int {
        val widths = FloatArray(text.length)
        paintText.getTextWidths(text, widths)
        val result = widths.sum()
        return Math.round(result)
    }

    fun draw(graphObj: SvgElement) {
        setGradientFill(false)
        val oldXfer = paintStroke.xfermode
        if (composite != null) {
            paintStroke.xfermode = composite
        }
        graphObj.draw(canvas, paintStroke)

        if (composite != null) {
            paintStroke.xfermode = oldXfer
        }
        clearGradient(false)
    }

    fun fill(graphObj: SvgElement) {
        setGradientFill(true)
        val oldXfer = paintFill.xfermode
        if (composite != null) {
            paintFill.xfermode = composite
        }
        graphObj.draw(canvas, paintFill)

        if (composite != null) {
            paintFill.xfermode = oldXfer
        }
        clearGradient(true)
    }

    fun drawString(str: String, fx: Float, fy: Float) {
        canvas.drawText(str, fx, fy, paintText)
    }

    fun setFont(name: Array<String>, style: Int, size: Int, externalSupport: ExternalSupport) {
        fontNames = name
        val typeface = createNewFont(name, style, externalSupport)
        paintText.textSize = size.toFloat()
        paintText.typeface = typeface
    }

    fun deriveFont() {}

    fun deriveFont(style: Int, externalSupport: ExternalSupport) {
        val typeface = createNewFont(fontNames, style, externalSupport)
        paintText.typeface = typeface
    }

    fun deriveFont(size: Float) {
        paintText.textSize = size
    }

    fun deriveFont(style: Int, size: Int, externalSupport: ExternalSupport) {
        val typeface = createNewFont(fontNames, style, externalSupport)
        paintText.textSize = size.toFloat()
        paintText.typeface = typeface
    }

    fun getFontSize(): Int = Math.round(paintText.textSize)

    fun getFontStyle(): Int = paintText.typeface.style

    private fun getExternalFont(fontName: String, fontStyle: Int, externalSupport: ExternalSupport?): Typeface? =
            externalSupport?.getExternalFont(fontName, fontStyle)

    private fun createNewFont(names: Array<String>?, style: Int, externalSupport: ExternalSupport): Typeface {
        var typeface: Typeface? = null
        if (names != null) {
            for (name in names) {
                val dequotedName = dequote(name)
                typeface = checkAndroidFont(dequotedName, style)
                if (typeface == null && names[0] == dequotedName) {
                    typeface = getExternalFont(dequotedName, style, externalSupport)
                }
                if (typeface != null) {
                    break
                }
            }
        }

        return if (typeface == null) {
            Typeface.create(Typeface.DEFAULT, style)
        } else {
            typeface
        }
    }

    private fun checkAndroidFont(fontName: String, fontStyle: Int): Typeface? {
        var font: Typeface? = null

        when (fontName) {
            "serif" -> font = Typeface.create(Typeface.SERIF, fontStyle)
            "sans-serif" -> font = Typeface.create(Typeface.SANS_SERIF, fontStyle)
            "monospace" -> font = Typeface.create(Typeface.MONOSPACE, fontStyle)
        }
        return font
    }

    private fun colorsProcess(colorArray: Array<ColorSvg>): IntArray {
        val colors = IntArray(colorArray.size)
        for ((i, color) in colorArray.withIndex()) {
            colors[i] = color.value
        }
        return colors
    }

    fun setGradient(gradient: RadialGradientPaint, alphaColor: ColorSvg) {
        val colors = colorsProcess(gradient.colors)
        val radialGradient = RadialGradient(gradient.x, gradient.y, gradient.radius, colors, gradient.positions, gradient.tileMode)
        this.gradient = radialGradient
        (this.gradient as RadialGradient).setLocalMatrix(gradient.matrix)
        gradientOpacity = alphaColor
    }

    fun setGradient(gradient: LinearGradientPaint, alphaColor: ColorSvg) {
        val colors = colorsProcess(gradient.colors)
        val linearGradient = LinearGradient(gradient.x1, gradient.y1, gradient.x2, gradient.y2, colors, gradient.positions, gradient.mTileMode)
        this.gradient = linearGradient
        (this.gradient as LinearGradient).setLocalMatrix(gradient.matrix)
        gradientOpacity = alphaColor
    }

    private fun setGradientFill(isFill: Boolean) {
        val paint = if (isFill) {
            paintFill
        } else {
            paintStroke
        }

        if (gradient != null) {
            if (isFill) {
                storedPaintFillColor = paint.color
            } else {
                storedPaintStrokeColor = paint.color
            }
            gradientOpacity?.let {
                paint.color = it.value
            }
            paint.shader = gradient
        }
    }

    private fun clearGradient(isFill: Boolean) {
        val paint = if (isFill) {
            paintFill
        } else {
            paintStroke
        }
        paint.color = if (isFill) {
            storedPaintFillColor
        } else {
            storedPaintStrokeColor
        }
        paint.shader = null
        gradient = null
        gradientOpacity = null
    }

    companion object {
        
        val MATRIX: String = "matrix"
        val COLOR: String = "color"

        fun dequote(token: String?): String {
            if (token == null) {
                return ""
            }

            val t = token.trim { it <= ' ' }

            return if (t.startsWith("\"") && t.endsWith("\"") || t.startsWith("'") && t.endsWith("'")) {
                t.substring(1, t.length - 1).trim { it <= ' ' }
            } else {
                t
            }
        }
    }
}