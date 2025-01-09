package me.rhunk.abdooenhance.common.config.impl

import me.rhunk.abdooenhance.common.config.ConfigContainer
import me.rhunk.abdooenhance.common.config.PropertyValue
import me.rhunk.abdooenhance.common.data.MessagingRuleType
import me.rhunk.abdooenhance.common.data.RuleState


class Rules : ConfigContainer() {
    private val rules = mutableMapOf<MessagingRuleType, PropertyValue<String>>()

    fun getRuleState(ruleType: MessagingRuleType): RuleState? {
        return rules[ruleType]?.getNullable()?.let { RuleState.getByName(it) }
    }

    init {
        MessagingRuleType.entries.filter { it.listMode }.forEach { ruleType ->
            rules[ruleType] = unique(ruleType.key,"whitelist", "blacklist") {
                customTranslationPath = "rules.properties.${ruleType.key}"
                customOptionTranslationPath = "rules.modes"
                addNotices(*ruleType.configNotices)
            }.apply {
                set(ruleType.defaultValue)
            }
        }
    }
}