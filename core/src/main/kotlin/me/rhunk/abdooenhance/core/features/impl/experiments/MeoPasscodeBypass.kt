package me.rhunk.abdooenhance.core.features.impl.experiments

import me.rhunk.abdooenhance.core.features.Feature
import me.rhunk.abdooenhance.core.features.FeatureLoadParams
import me.rhunk.abdooenhance.core.util.hook.HookStage
import me.rhunk.abdooenhance.core.util.hook.hook
import me.rhunk.abdooenhance.mapper.impl.BCryptClassMapper

class MeoPasscodeBypass : Feature("Meo Passcode Bypass", loadParams = FeatureLoadParams.ACTIVITY_CREATE_ASYNC) {
    override fun asyncOnActivityCreate() {
        if (!context.config.experimental.meoPasscodeBypass.get()) return

        context.mappings.useMapper(BCryptClassMapper::class) {
            classReference.get()?.hook(
                hashMethod.get()!!,
                HookStage.BEFORE,
            ) { param ->
                //set the hash to the result of the method
                param.setResult(param.arg(1))
            }
        }
    }
}