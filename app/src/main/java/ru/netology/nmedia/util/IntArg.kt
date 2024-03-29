package ru.netology.nmedia.util

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object IntArg: ReadWriteProperty<Bundle, Int> {
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int) {
        value?.let { thisRef.putInt(property.name, it) }
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Int {
        return thisRef.getInt(property.name)
    }



}