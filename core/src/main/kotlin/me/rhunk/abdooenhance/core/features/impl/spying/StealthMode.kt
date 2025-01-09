package me.rhunk.abdooenhance.core.features.impl.spying

import me.rhunk.abdooenhance.common.data.MessagingRuleType
import me.rhunk.abdooenhance.core.event.events.impl.OnSnapInteractionEvent
import me.rhunk.abdooenhance.core.features.FeatureLoadParams
import me.rhunk.abdooenhance.core.features.MessagingRuleFeature
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.hook
import me.rhunk.abdooenhance.core.wrapper.impl.SnapUUID
import java.util.concurrent.CopyOnWriteArraySet

class StealthMode : MessagingRuleFeature("StealthMode", MessagingRuleType.STEALTH, loadParams = FeatureLoadParams.INIT_SYNC) {
    private val displayedMessageQueue = CopyOnWriteArraySet<Long>()

    fun addDisplayedMessageException(clientMessageId: Long) {
        displayedMessageQueue.add(clientMessageId)
    }

    override fun init() {
        val isConversationInStealthMode: (SnapUUID) -> Boolean = hook@{
            context.feature(StealthMode::class).canUseRule(it.toString())
        }

        arrayOf("mediaMessagesDisplayed", "displayedMessages").forEach { methodName: String ->
            context.classCache.conversationManager.hook(methodName, HookStage.BEFORE) { param ->
                if (displayedMessageQueue.removeIf { param.arg<Long>(1) == it }) return@hook
                if (isConversationInStealthMode(SnapUUID(param.arg(0)))) {
                    param.setResult(null)
                }
            }
        }

        context.event.subscribe(OnSnapInteractionEvent::class) { event ->
            if (isConversationInStealthMode(event.conversationId)) {
                event.canceled = true
            }
        }
    }
}