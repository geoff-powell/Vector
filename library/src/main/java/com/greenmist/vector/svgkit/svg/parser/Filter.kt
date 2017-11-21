package com.greenmist.vector.svgkit.svg.parser

import com.greenmist.vector.extension.isNotNullOrBlank
import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.svgkit.svg.filters.FilterType
import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class Filter {

    var op: ArrayList<FilterOp> = ArrayList()
    var id: String? = null
    var filterUnits: String? = null

    fun buildFilterImpl(fop: FilterOp): FilterType? {
        val fopName = fop.filterOp.substring(0, 1).toUpperCase() + fop.filterOp.substring(1)
        val cz = "com.scand.svg.parser.paintutil." + fopName
        try {
            val cc = Class.forName(cz)
            return cc.newInstance() as FilterType
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    private fun optimize(filterOp: FilterOp, results: HashMap<String, FilterOp>, newOps: ArrayList<FilterOp>) {
        if (newOps.contains(filterOp)) {
            return
        }

        val inop = if (filterOp.`in`.isNotNullOrBlank()) {
            results[filterOp.`in`]
        } else {
            null
        }
        val inop2 = if (filterOp.in2.isNotNullOrBlank()) {
            results[filterOp.in2]
        } else {
            null
        }

        // current have unresolved dependency...
        if (inop != null) {
            optimize(inop, results, newOps)
        }
        if (inop2 != null) {
            optimize(inop2, results, newOps)
        }
        newOps.add(filterOp)
    }

    fun optimize() {
        // we need to order all filters according to in, in2 and result clauses to let them work in right order
        // by the way we also  need to compute the infiltrated area but it is TODO

        // this trick is necessary just because all filters are specular and it ain't works
        // as ins SVG specs.It just specifies canvas' options like color filters, without real image composing.
        val newOps = ArrayList<FilterOp>()

        val results = HashMap<String, FilterOp>()
        op.filter {
            it.result.isNotNullOrBlank()
        }.forEach {
            it.result?.let { result ->
                results.put(result, it)
            }
        }

        op.indices.map {
            op[it]
        }.forEach {
            optimize(it, results, newOps)
        }

        this.op = newOps

        SvgLogger.i("Fop[" + this.id + "," + this.filterUnits + "]{")
        op.forEach {
            SvgLogger.i( "   " + it.filterOp + ", {" + it.`in` + "," + it.in2 + "} => " + it.result)
        }
        SvgLogger.i( "}Fop")
    }

    companion object {
        var knownFields = arrayOf(
                "feBlend", "feColorMatrix", "feComponentTransfer", "feComposite",
                "feConvolveMatrix", "feDiffuseLighting", "feDisplacementMap",
                "feDistantLight", "feFlood", "feFuncA", "feFuncR", "feFuncG",
                "feFuncB", "feGaussianBlur", "feImage", "feMerge", "feMergeNode",
                "fefeMorphology", "feOffset", "fePointLight", "feSpecularLighting",
                "feSpotLight", "feTile", "feTurbulence")

        fun isFilterOp(localNode: String): Boolean =
                knownFields.indices.any { knownFields[it] == localNode }


        fun buildFilterImpl(fop: FilterOp): FilterType? {
            val filterOpName = fop.filterOp.substring(0, 1).toUpperCase() + fop.filterOp.substring(1)
            val className = "com.greenmist.vector.svgkit.svg.filters" + filterOpName
            try {
                val cc = Class.forName(className)
                return cc.newInstance() as FilterType
            } catch (e: ClassNotFoundException) {
                SvgLogger.e(e,"Class not found: $className")
            } catch (e: InstantiationException) {
                SvgLogger.e(e,"Couldn't instantiate: $className")
            } catch (e: IllegalAccessException) {
                SvgLogger.e(e,"Illegal access of class: $className")
            }

            return null
        }
    }
}