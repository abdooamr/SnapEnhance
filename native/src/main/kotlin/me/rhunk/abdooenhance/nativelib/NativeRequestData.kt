package me.rhunk.abdooenhance.nativelib

data class NativeRequestData(
    val uri: String,
    var buffer: ByteArray,
    var canceled: Boolean = false,
)