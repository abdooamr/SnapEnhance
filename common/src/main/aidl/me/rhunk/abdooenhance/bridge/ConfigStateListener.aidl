package me.rhunk.abdooenhance.bridge;

oneway interface ConfigStateListener {
    void onConfigChanged();
    void onRestartRequired();
    void onCleanCacheRequired();
}