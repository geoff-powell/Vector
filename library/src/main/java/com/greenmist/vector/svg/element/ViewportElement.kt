package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.Viewport
import com.greenmist.vector.svg.model.PreserveAspectRatio

interface ViewportElement : ViewBoxElement {

    var viewport: Viewport
    var preserveAspectRatio : PreserveAspectRatio?
}