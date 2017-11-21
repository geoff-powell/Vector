package com.greenmist.vector.svgkit.parser

/**
 * Created by geoffpowell on 11/17/17.
 *//*
class SvgContentHandler : DefaultHandler() {

    private var left: Int = 0
    private var top: Int = 0
    private var baseWidth: Int = 0
    private var baseHeight: Int = 0
    private var reqWidth: Int = 0
    private var reqHeight: Int = 0
    private val g: GraphicsSvg? = null
    private var scaleX: Float = 0.toFloat()
    private var scaleY: Float = 0.toFloat()

    internal var picture: Bitmap? = null
    internal var canvas: GraphicsSvg? = null

    internal var rect = RectF()
    internal var bounds: RectF? = null

    internal var idleMode = false

    private var hidden = false
    private var hiddenLevel = 0
    private var boundsMode: Boolean = false
    private var patternDefinition: Boolean = false
    private var metaData: Boolean = false
    private var defs: Boolean = false

    private val ctxs = Stack()
    private val gpushed = Stack<Boolean>()

    private var runStyle = false
    private var style = ""

    private var currentText: Text? = null

    internal var gradientMap: HashMap<String, Gradient> = HashMap()
    internal var gradientRefMap: HashMap<String, Gradient> = HashMap()
    internal var clipMap: HashMap<String, ClipPath> = HashMap()
    internal var gradient: Gradient? = null

    private val stylesheets = Vector()
    private val cascadeEngine = CascadeEngine()
    private val cssParser = CSSParser()
    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    private var ww: Float = 0.toFloat()
    private var hh: Float = 0.toFloat()

    private var bKeepAspectRatio = true
    private var bCropImage = false

    private var parser: SVGParser? = null
    private var specialGraphicsMode: Boolean = false

    private var movedOriginX = 0
    private var movedOriginY = 0

    // defs parsings
    internal var defx = HashMap<String, StringBuffer>()
    internal var sbx = StringBuffer()

    internal var svgNesting = 0
    internal var recursion: Boolean = false


    // filters parsing
    private var curFilter: Filter? = null
    private val feStack = Stack()
    private val filters = HashMap()

    internal var limits = RectF(
            java.lang.Float.POSITIVE_INFINITY,
            java.lang.Float.POSITIVE_INFINITY,
            java.lang.Float.NEGATIVE_INFINITY,
            java.lang.Float.NEGATIVE_INFINITY)


    internal fun getImage(): Bitmap? = picture

    fun setIdleMode(mode: Boolean) {
        this.idleMode = mode
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        // Set up prior to parsing a doc
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        // Clean up after parsing a doc
    }

    private fun applyFilterChain(canvas: GraphicsSvg, c: PaintData, stroke: Boolean, bounds: RectF) {
        c.filter?.op?.forEach { filterOp ->
            val impl = Filter.buildFilterImpl(filterOp)
            if (impl != null) {
                impl.handle(filterOp, canvas, stroke, bounds)
            } else {
                Logger.w("${filterOp.filterOp}: not implemented")
            }
        }
    }

    private fun setFill(atts: Properties, c: PaintData?, gradients: HashMap<*, *>, bounds: RectF): Boolean {
        //Log.d(LOG_TAG, "setFill");
        if ("none" == atts.getString("display")) {
            return false
        }

        val v = atts.getProperty("fill")
        if ("none" == v) {
            return false
        }

        if (idleMode) {
            return true
        }

        c?.let {
            canvas?.let { canvas ->
                applyFilterChain(canvas, c, false, bounds)
            }
        }

        val fillString = atts.getString("fill")
        if (fillString != null && fillString.startsWith("url(#")) {
            val end = fillString.indexOf(")")
            val id = fillString.substring("url(#".length, end)
            val gr = gradients[id] as Gradient
            try {
                // Log.d(LOG_TAG, "set gradient " + gr.id);
                val alphaColor = atts.getOpacityColorForGradient("fill", parentContext())
                ShaderApply.applyShader(gr, bounds.left, bounds.top, bounds.width(), bounds.height(), canvas, alphaColor)
            } catch (e: Throwable) { // NoClassdefFoundError
                e.printStackTrace()
            }

            return true
        } else {
            val color = atts.getColor("fill", parentContext())
            if (color != null) {
                if (color.getAlpha() === 0 || color.isNone) {
                    return false
                }
                canvas?.setFillColor(color.value)
                return true
            } else if (atts.getString("fill") == null /*&& (atts.getString("stroke") == null)*/) {
                canvas?.setFillColor(android.graphics.Color.BLACK)
                //canvas.setStrokeColor(android.graphics.Color.BLACK);
                return true
            }
        }
        return false
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)

        var elementName = localName
        if (qName.isNotNullOrBlank()) {
            elementName = qName
        }

        val attrMap = if (attributes != null) AttrMapAttributeAdapter(attributes) else AttrMapImpl()

        cascadeEngine.pushElement(getNamespace(), localName, attrMap)

        val style = attributes?.getValue("style")
        if (style != null) {
            val inlineStyle = cssParser.readInlineStyle(style)
            cascadeEngine.applyInlineRule(inlineStyle)
        }
        val cascadeResult = cascadeEngine.getCascadeResult()

        val elementProperties = cascadeResult.getProperties()
        val elementStyle = elementProperties.getPropertySet()

        val properties = Properties(attributes, elementStyle)

        when (localName) {
            "rect" -> mGroup.addElement(parseRect(properties, true))
            "circle" -> mGroup.addElement(parseCircle(properties))
            "ellipse" -> mGroup.addElement(parseEllipse(properties))
            "line" -> mGroup.addElement(parseLine(pp))
            "polygon", "polyline" -> mGroup.addElement(parsePolygon(pp, localName == "polygon"))
            "path" -> mGroup.addElement(parsePath(pp))
        }
    }

    private fun doClipPath(pp: Properties, canvas: GraphicsSvg): ClipPath? {
        if (idleMode) {
            return null
        }

        val clipPathString = pp.getProperty("clip-path")
        if (clipPathString != null && clipPathString.startsWith("url(#")) {
            val id = clipPathString.substring("url(#".length, clipPathString.length - 1)
            var clipPath: ClipPath? = clipMap.get(id)
            if (clipPath == null) {
                val sub = defx.get(id)
                try {
                    clipPath = parseClipPath(sub, canvas)
                } catch (e: SAXException) {
                    e.printStackTrace()
                } catch (e: ParserConfigurationException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                clipMap.put(id, clipPath)
            }
            return clipPath
        } else {
            return null
        }
    }

    private fun getNamespace(): String = "http://www.w3.org/2000/svg"

    fun getSvg(): Svg {
        return Svg()
    }
}
*/