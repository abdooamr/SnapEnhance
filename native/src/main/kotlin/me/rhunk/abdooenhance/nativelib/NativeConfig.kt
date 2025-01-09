package me.rhunk.abdooenhance.nativelib

data class NativeConfig(
    val disableBitmoji: Boolean = false,
    val disableMetrics: Boolean = false,
    val hookAssetOpen: Boolean = false,
)