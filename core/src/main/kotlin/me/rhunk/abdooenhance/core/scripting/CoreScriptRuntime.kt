package me.rhunk.abdooenhance.core.scripting

import me.rhunk.abdooenhance.bridge.scripting.AutoReloadListener
import me.rhunk.abdooenhance.bridge.scripting.IScripting
import me.rhunk.abdooenhance.common.logger.AbstractLogger
import me.rhunk.abdooenhance.common.scripting.ScriptRuntime
import me.rhunk.abdooenhance.common.scripting.bindings.BindingSide
import me.rhunk.abdooenhance.core.ModContext
import me.rhunk.abdooenhance.core.scripting.impl.CoreEvents
import me.rhunk.abdooenhance.core.scripting.impl.CoreIPC
import me.rhunk.abdooenhance.core.scripting.impl.CoreMessaging
import me.rhunk.abdooenhance.core.scripting.impl.CoreScriptConfig
import me.rhunk.abdooenhance.core.scripting.impl.CoreScriptHooker

class CoreScriptRuntime(
    private val modContext: ModContext,
    logger: AbstractLogger,
): ScriptRuntime(modContext.androidContext, logger) {
    fun connect(scriptingInterface: IScripting) {
        scripting = scriptingInterface
        scriptingInterface.apply {
            buildModuleObject = { module ->
                putConst("currentSide", this, BindingSide.CORE.key)
                module.registerBindings(
                    CoreScriptConfig(),
                    CoreIPC(),
                    CoreScriptHooker(),
                    CoreMessaging(modContext),
                    CoreEvents(modContext),
                )
            }

            enabledScripts.forEach { path ->
                runCatching {
                    load(path, scriptingInterface.getScriptContent(path))
                }.onFailure {
                    logger.error("Failed to load script $path", it)
                }
            }

            registerAutoReloadListener(object : AutoReloadListener.Stub() {
                override fun restartApp() {
                    modContext.softRestartApp()
                }
            })
        }
    }
}