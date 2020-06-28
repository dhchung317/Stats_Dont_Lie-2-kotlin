package com.hyunki.statsdontlie2.view.fragments.game.delegates

import org.w3c.dom.Text

class TextSize(val s: String) {
    val size: Float by TextSizeDelegate(s)
}