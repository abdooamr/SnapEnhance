package me.rhunk.abdooenhance

import me.rhunk.abdooenhance.bridge.logger.TrackerInterface
import me.rhunk.abdooenhance.common.data.TrackerEventsResult
import me.rhunk.abdooenhance.common.data.TrackerRule
import me.rhunk.abdooenhance.common.data.TrackerRuleEvent
import me.rhunk.abdooenhance.common.util.toSerialized


class RemoteTracker(
    private val context: RemoteSideContext
): TrackerInterface.Stub() {
    fun init() {
        /*TrackerEventType.entries.forEach { eventType ->
            val ruleId = context.modDatabase.addTrackerRule(TrackerFlags.TRACK or TrackerFlags.LOG or TrackerFlags.NOTIFY, null, null)
            context.modDatabase.addTrackerRuleEvent(ruleId, TrackerFlags.TRACK or TrackerFlags.LOG or TrackerFlags.NOTIFY, eventType.key)
        }*/
    }

    override fun getTrackedEvents(eventType: String): String? {
        val events = mutableMapOf<TrackerRule, MutableList<TrackerRuleEvent>>()

        context.modDatabase.getTrackerEvents(eventType).forEach { (event, rule) ->
            events.getOrPut(rule) { mutableListOf() }.add(event)
        }

        return TrackerEventsResult(events).toSerialized()
    }
}