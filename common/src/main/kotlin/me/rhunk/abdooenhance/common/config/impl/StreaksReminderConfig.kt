package me.rhunk.abdooenhance.common.config.impl

import me.rhunk.abdooenhance.common.config.ConfigContainer

class StreaksReminderConfig : ConfigContainer(hasGlobalState = true) {
    val interval = integer("interval", 1)
    val remainingHours = integer("remaining_hours", 13)
    val groupNotifications = boolean("group_notifications", true)
}