package me.rhunk.abdooenhance.core.features.impl.ui

import me.rhunk.abdooenhance.core.features.Feature
import me.rhunk.abdooenhance.core.features.FeatureLoadParams
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.hookConstructor
import me.rhunk.abdooenhance.core.util.ktx.setObjectField
import me.rhunk.abdooenhance.mapper.impl.FriendingDataSourcesMapper

class HideQuickAddFriendFeed : Feature("HideQuickAddFriendFeed", loadParams = FeatureLoadParams.ACTIVITY_CREATE_SYNC) {
    override fun onActivityCreate() {
        if (!context.config.userInterface.hideQuickAddFriendFeed.get()) return

        context.mappings.useMapper(FriendingDataSourcesMapper::class) {
            classReference.getAsClass()?.hookConstructor(HookStage.AFTER) { param ->
                param.thisObject<Any>().setObjectField(
                    quickAddSourceListField.get()!!,
                    arrayListOf<Any>()
                )
            }
        }
    }
}