package me.rhunk.abdooenhance.common.scripting.bindings

import me.rhunk.abdooenhance.common.scripting.ScriptRuntime
import me.rhunk.abdooenhance.common.scripting.type.ModuleInfo

class BindingsContext(
    val moduleInfo: ModuleInfo,
    val runtime: ScriptRuntime
)