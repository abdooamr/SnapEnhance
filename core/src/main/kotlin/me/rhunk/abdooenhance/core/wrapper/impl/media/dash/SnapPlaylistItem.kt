package me.rhunk.abdooenhance.core.wrapper.impl.media.dash

import me.rhunk.abdooenhance.core.wrapper.AbstractWrapper

class SnapPlaylistItem (obj: Any?) : AbstractWrapper(obj) {
    val snapId by lazy {
        instanceNonNull().javaClass.declaredFields.first { it.type == Long::class.javaPrimitiveType }.get(instanceNonNull()) as Long
    }
}