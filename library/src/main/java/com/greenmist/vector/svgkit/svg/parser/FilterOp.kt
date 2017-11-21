package com.greenmist.vector.svgkit.svg.parser

import android.graphics.RectF
import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class FilterOp(val filterOp: String) {
    var bounds: RectF? = null /* x, y, width, height */
    var resX: Float? = null
    var resY:Float? = null /* rx, ry: not implemented 'filterRes' */
    var href: String? = null
    var result: String? = null

    var op = ArrayList<FilterOp>()

    /* common except fe*Light and feMergeNode */
    /* filterPrimitiveMarginsUnits filterMarginsUnits */
    /* filterUnits */


    /* feFlood */
    var `in`: String? = null

    /* feFunc* */
    var funcType = "A" /* A, R, G, B */
    var type = "table" /* identity, table, discrete, linear, gamma */
    var tableValues = ArrayList<Float>()
    var slope: Float? = 1f
    var intercept: Float? = 0f
    var amplitude: Float? = 1f
    var exponent: Float? = 1f
    var offset: Float? = 0f

    /* feMerge */
    /* feBlend */
    var mode = "normal" /* '', 'normal', 'multiply', 'screen', 'darken', 'lighten' */
    /* shared public String in; */
    var in2: String? = null

    /* feSpecularLighting */
    /* shared public String in; */
    var surfaceScale: Float? = 1f
    var specularConstant: Float? = 1f
    var specularExponent: Float? = 1f

    /* feTurbulence */
    var stitchTiles = "no-stitch" /* '', stitch, no-stitch */
    var feTurbulence_type = "turbulence" /* '', fractalNoise, turbulence */
    var numOctaves: Float? = 1f
    var seed: Float? = 0f

    /* feComposite */
    var operator = "over" /* '' over in out atop xor arithmetic  */
    /* shared private String in; */
    /* shared private String in2; */
    var k1: Float? = 0f
    var k2: Float? = 0f
    var k3: Float? = 0f
    var k4: Float? = 0f

    /* feColorMatrix */
    var feColorMatrix_type = "matrix" /* '' matrix saturate hueRotate luminanceToAlpha */
    /* shared public String in; */
    var values = ArrayList<Float>()

    /* feGaussianBlur */
    /* shared public String in; */
    /* std-deviation-[x|y] are not supported */

    /* fePointLight */
    var x: Float? = 0f
    var y: Float? = 0f
    var z: Float? = 0f

    /* feSpotLight */
    /* shared public Float x = 0f; */
    /* shared public Float y = 0f; */
    /* shared public Float z = 0f; */
    var pointsAtX: Float? = 0f
    var pointsAtY: Float? = 0f
    var pointsAtZ: Float? = 0f
    /* shared public Float specularExponent = 1f; */
    var limitingConeAngle: Float? = 0f

    /* feConvolveMatrix */
    var edgeMode = "duplicate" /* '' duplicate wrap none */
    /* shared public String in; */
    var bias: Float? = 0f
    var preserveAlpha = false
    /* unsupported: order kernelUnitLength kernelMatrix divisor targetX targetY */

    /* feImage */
    var preserveAspectRatio = false
    var externalResourcesRequired = false

    /* feComponentTransfer */
    /* shared public String in; */

    /* feOffset */
    /* shared private String in; */
    var dx: Float? = 0f
    var dy: Float? = 0f

    /* feDistantLight */
    var azimuth: Float? = 0f
    var elevation: Float? = 0f

    /* feMorphology */
    /* shared private public in; */
    var feMorphology_operator = "erode" /* '' erode dilate */
    /* radiusX radiusY are not supported */

    /* feDisplacementMap */
    /* shared public String in; */
    /* shared public String in2; */
    var scale: Float? = 0f
    var xChannelSelector = "A" /* '' R G B A */
    var yChannelSelector = "A" /* '' R G B A */

    /* feMergeNode */
    /* shared public String in; */

    /* feTile */
    /* shared public String in; */

    /* feDiffuseLighting */
    /* shared public String in; */
    /* shared public Float surfaceScale = 1f; */
    var diffuseConstant: Float? = 1f

    fun parseFrom(properties: Properties) {
        // As we have so much properties we'll fit it via reflections.
        // The defaults are coming from fields.
        for (field in javaClass.declaredFields) {
            val fieldType = field.type
            var propName = field.name
            if (propName.startsWith(filterOp)) {
                propName = propName.substring(propName.indexOf('_') + 1)
            }

            try {
                if (fieldType == Float::class.java) {
                    val v = properties.getFloat(propName, field.getFloat(this))
                    field.setFloat(this, v!!)
                } else if (fieldType == String::class.java) {
                    var v = properties.getString(propName)
                    if (v == null) {
                        v = field.get(this) as String
                    }

                    field.set(this, v)
                } else if (fieldType == Boolean::class.java || fieldType == Boolean::class.javaPrimitiveType) {
                    var v = properties.getString(propName)
                    if (v == null) {
                        v = "" + field.getBoolean(this)
                    }

                    field.setBoolean(this, v == "true")
                }
            } catch (e: Exception) {
                continue
            }
        }
    }

}