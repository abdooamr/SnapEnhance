package me.rhunk.abdooenhance.core.event.events.impl

import me.rhunk.abdooenhance.core.event.Event
import me.rhunk.abdooenhance.core.wrapper.impl.Message

class BuildMessageEvent(
    val message: Message
): Event()