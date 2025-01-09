package me.rhunk.abdooenhance.common.config.impl

import me.rhunk.abdooenhance.common.config.ConfigContainer
import me.rhunk.abdooenhance.common.config.ConfigFlag

class Scripting : ConfigContainer() {
    val developerMode = boolean("developer_mode", false) { requireRestart() }
    val moduleFolder = string("module_folder", "modules") { addFlags(ConfigFlag.FOLDER); requireRestart()  }
    val autoReload = unique("auto_reload", "snapchat_only", "all")
    val integratedUI = boolean("integrated_ui", false) { requireRestart() }
    val disableLogAnonymization = boolean("disable_log_anonymization", false) { requireRestart() }
}