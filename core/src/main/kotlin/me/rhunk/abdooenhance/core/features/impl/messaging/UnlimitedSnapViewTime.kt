package me.rhunk.abdooenhance.core.features.impl.messaging

import me.rhunk.abdooenhance.common.data.ContentType
import me.rhunk.abdooenhance.common.data.MessageState
import me.rhunk.abdooenhance.common.util.protobuf.ProtoEditor
import me.rhunk.abdooenhance.common.util.protobuf.ProtoReader
import me.rhunk.abdooenhance.core.event.events.impl.BuildMessageEvent
import me.rhunk.abdooenhance.core.features.Feature
import me.rhunk.abdooenhance.core.features.FeatureLoadParams

class UnlimitedSnapViewTime :
    Feature("UnlimitedSnapViewTime", loadParams = FeatureLoadParams.ACTIVITY_CREATE_SYNC) {
    override fun onActivityCreate() {
        val state by context.config.messaging.unlimitedSnapViewTime

        context.event.subscribe(BuildMessageEvent::class, { state }, priority = 101) { event ->
            if (event.message.messageState != MessageState.COMMITTED) return@subscribe
            if (event.message.messageContent!!.contentType != ContentType.SNAP) return@subscribe

            val messageContent = event.message.messageContent

            val mediaAttributes = ProtoReader(messageContent!!.content!!).followPath(11, 5, 2) ?: return@subscribe
            if (mediaAttributes.contains(6)) return@subscribe
            messageContent.content = ProtoEditor(messageContent.content!!).apply {
                edit(11, 5, 2) {
                    remove(8)
                    addBuffer(6, byteArrayOf())
                }
            }.toByteArray()
        }
    }
}
