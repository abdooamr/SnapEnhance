package me.rhunk.abdooenhance.core.event.events.impl

import me.rhunk.abdooenhance.core.event.events.AbstractHookEvent
import me.rhunk.abdooenhance.core.wrapper.impl.SnapUUID

class OnSnapInteractionEvent(
    val interactionType: String,
    val conversationId: SnapUUID,
    val messageId: Long
) : AbstractHookEvent()