package com.hyunki.statsdontlie2.view.fragments.game.delegates

import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class TextSizeDelegate(val string: String){
    operator fun getValue(textSize: TextSize, property: KProperty<*>): Float {
        return when (textSize.s.length > 8) {
            true -> 34f
            else -> 36f
        }
    }
}