package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.model.Length

interface BoxElement : PositionElement {
    var width: Length?
    var height: Length?
}