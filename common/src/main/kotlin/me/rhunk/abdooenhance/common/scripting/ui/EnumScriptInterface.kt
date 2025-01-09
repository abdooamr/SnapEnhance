package me.rhunk.abdooenhance.common.scripting.ui

import me.rhunk.abdooenhance.common.scripting.bindings.BindingSide

enum class EnumScriptInterface(
    val key: String,
    val side: BindingSide
) {
    SETTINGS("settings", BindingSide.MANAGER),
    FRIEND_FEED_CONTEXT_MENU("friendFeedContextMenu", BindingSide.CORE),
    CONVERSATION_TOOLBOX("conversationToolbox", BindingSide.CORE),
}