package me.rhunk.abdooenhance.core.features.impl.messaging

import me.rhunk.abdooenhance.common.data.NotificationType
import me.rhunk.abdooenhance.common.util.protobuf.ProtoEditor
import me.rhunk.abdooenhance.core.event.events.impl.NativeUnaryCallEvent
import me.rhunk.abdooenhance.core.event.events.impl.SendMessageWithContentEvent
import me.rhunk.abdooenhance.core.features.Feature
import me.rhunk.abdooenhance.core.features.FeatureLoadParams
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.hook

class PreventMessageSending : Feature("Prevent message sending", loadParams = FeatureLoadParams.ACTIVITY_CREATE_ASYNC) {
    override fun asyncOnActivityCreate() {
        val preventMessageSending by context.config.messaging.preventMessageSending

        context.event.subscribe(NativeUnaryCallEvent::class, { preventMessageSending.contains("snap_replay") }) { event ->
            if (event.uri != "/messagingcoreservice.MessagingCoreService/UpdateContentMessage") return@subscribe
            event.buffer = ProtoEditor(event.buffer).apply {
                edit(3) {
                    // replace replayed to read receipt
                    if (firstOrNull(13) != null) {
                        remove(13)
                        addBuffer(4, byteArrayOf())
                    }
                }
            }.toByteArray()
        }

        context.classCache.conversationManager.hook("updateMessage", HookStage.BEFORE) { param ->
            val messageUpdate = param.arg<Any>(2).toString()
            if (messageUpdate == "SCREENSHOT" && preventMessageSending.contains("chat_screenshot")) {
                param.setResult(null)
            }

            if (messageUpdate == "SCREEN_RECORD" && preventMessageSending.contains("chat_screen_record")) {
                param.setResult(null)
            }
        }

        context.event.subscribe(SendMessageWithContentEvent::class) { event ->
            val contentType = event.messageContent.contentType
            val associatedType = NotificationType.fromContentType(contentType ?: return@subscribe) ?: return@subscribe

            if (preventMessageSending.contains(associatedType.key)) {
                context.log.verbose("Preventing message sending for $associatedType")
                event.canceled = true
            }
        }
    }
}