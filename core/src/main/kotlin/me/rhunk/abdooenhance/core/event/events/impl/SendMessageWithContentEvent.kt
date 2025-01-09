package me.rhunk.abdooenhance.core.event.events.impl

import me.rhunk.abdooenhance.core.event.events.AbstractHookEvent
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.Hooker
import me.rhunk.abdooenhance.core.wrapper.impl.MessageContent
import me.rhunk.abdooenhance.core.wrapper.impl.MessageDestinations

class SendMessageWithContentEvent(
    val destinations: MessageDestinations,
    val messageContent: MessageContent,
    private val callback: Any
) : AbstractHookEvent() {

    fun addCallbackResult(methodName: String, block: (args: Array<Any?>) -> Unit) {
        Hooker.ephemeralHookObjectMethod(
            callback::class.java,
            callback,
            methodName,
            HookStage.BEFORE
        ) { block(it.args()) }
    }
}