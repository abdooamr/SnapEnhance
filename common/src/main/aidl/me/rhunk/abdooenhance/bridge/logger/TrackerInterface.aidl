package me.rhunk.abdooenhance.bridge.logger;

interface TrackerInterface {
    String getTrackedEvents(String eventType); // returns serialized TrackerEventsResult
}