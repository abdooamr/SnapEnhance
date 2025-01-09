package me.rhunk.abdooenhance.manager.data.download

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SEArtifact(
    val fileName: String,
    val size: Long,
    val downloadUrl: String,
) : Parcelable