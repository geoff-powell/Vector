package com.greenmist.vector.svg.css

import com.greenmist.vector.svg.exception.SvgParseException

enum class CssDisplay(value: String? = null) {
    INLINE,
    BLOCK,
    LIST_ITEM("list-item"),
    RUN_IN("run-in"),
    COMPACT,
    MARKER,
    TABLE,
    INLINE_TABLE("inline-table"),
    TABLE_ROW_GROUP("table-row-group"),
    TABLE_HEADER_GROUP("table-header-group"),
    TABLE_ROW("table-row"),
    TABLE_COLUMN_GROUP("table-column-group"),
    TABLE_COLUMN("table-column"),
    TABLE_CELL("table-cell"),
    TABLE_CAPTION("table-caption"),
    NONE;

    val value: String = value ?: name.toLowerCase()

    companion object {
        fun fromString(string: String): CssDisplay? {
            return CssDisplay.values().firstOrNull {
                it.value == string
            }
        }
    }
}

fun String.toCssDisplay(): CssDisplay {
    return CssDisplay.fromString(this) ?: throw SvgParseException("Could not parse $this to CssDisplay")
}