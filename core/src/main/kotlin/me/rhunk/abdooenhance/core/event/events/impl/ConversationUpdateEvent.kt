package me.rhunk.abdooenhance.core.event.events.impl

import me.rhunk.abdooenhance.core.event.events.AbstractHookEvent
import me.rhunk.abdooenhance.core.wrapper.impl.Message

class ConversationUpdateEvent(
    val conversationId: String,
    val conversation: Any?,
    val messages: List<Message>
) : AbstractHookEvent()