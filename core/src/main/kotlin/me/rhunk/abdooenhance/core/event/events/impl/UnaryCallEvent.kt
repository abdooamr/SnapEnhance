package me.rhunk.abdooenhance.core.event.events.impl

import me.rhunk.abdooenhance.core.event.events.AbstractHookEvent

class UnaryCallEvent(
    val uri: String,
    var buffer: ByteArray
): AbstractHookEvent() {
    val callbacks = mutableListOf<(UnaryCallEvent) -> Unit>()

    fun addResponseCallback(responseCallback: UnaryCallEvent.() -> Unit) {
        callbacks.add(responseCallback)
    }
}