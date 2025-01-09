package me.rhunk.abdooenhance.core.features.impl.ui

import me.rhunk.abdooenhance.common.data.MessagingRuleType
import me.rhunk.abdooenhance.common.data.RuleState
import me.rhunk.abdooenhance.core.features.FeatureLoadParams
import me.rhunk.abdooenhance.core.features.MessagingRuleFeature
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.hook
import me.rhunk.abdooenhance.core.util.hook.hookConstructor
import me.rhunk.abdooenhance.core.util.ktx.getObjectField
import me.rhunk.abdooenhance.core.util.ktx.setObjectField
import me.rhunk.abdooenhance.core.wrapper.impl.SnapUUID

class PinConversations : MessagingRuleFeature("PinConversations", MessagingRuleType.PIN_CONVERSATION, loadParams = FeatureLoadParams.ACTIVITY_CREATE_SYNC) {
    override fun onActivityCreate() {
        context.classCache.feedManager.hook("setPinnedConversationStatus", HookStage.BEFORE) { param ->
            val conversationUUID = SnapUUID(param.arg(0))
            val isPinned = param.arg<Any>(1).toString() == "PINNED"
            setState(conversationUUID.toString(), isPinned)
        }

        context.classCache.conversation.hookConstructor(HookStage.AFTER) { param ->
            val instance = param.thisObject<Any>()
            val conversationUUID = SnapUUID(instance.getObjectField("mConversationId"))
            if (getState(conversationUUID.toString())) {
                instance.setObjectField("mPinnedTimestampMs", 1L)
            }
        }

        context.classCache.feedEntry.hookConstructor(HookStage.AFTER) { param ->
            val instance = param.thisObject<Any>()
            val conversationUUID = SnapUUID(instance.getObjectField("mConversationId") ?: return@hookConstructor)
            val isPinned = getState(conversationUUID.toString())
            if (isPinned) {
                instance.setObjectField("mPinnedTimestampMs", 1L)
            }
        }
    }

    override fun getRuleState() = RuleState.WHITELIST
}