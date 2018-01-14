package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.svg.model.PreserveAspectRatio

interface ViewportElement {

    var viewBox : ViewBox?
    var preserveAspectRatio : PreserveAspectRatio?
}