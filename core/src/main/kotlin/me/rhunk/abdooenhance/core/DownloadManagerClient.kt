package me.rhunk.abdooenhance.core

import android.content.Intent
import android.os.Bundle
import me.rhunk.abdooenhance.bridge.DownloadCallback
import me.rhunk.abdooenhance.common.ReceiversConfig
import me.rhunk.abdooenhance.common.data.download.*
import me.rhunk.abdooenhance.core.features.impl.downloader.decoder.AttachmentType

class DownloadManagerClient (
    private val context: ModContext,
    private val metadata: DownloadMetadata,
    private val callback: DownloadCallback
) {
    private fun enqueueDownloadRequest(request: DownloadRequest) {
        context.bridgeClient.enqueueDownload(Intent().apply {
            putExtras(Bundle().apply {
                putString(ReceiversConfig.DOWNLOAD_REQUEST_EXTRA, context.gson.toJson(request))
                putString(ReceiversConfig.DOWNLOAD_METADATA_EXTRA, context.gson.toJson(metadata))
            })
        }, callback)
    }

    fun downloadDashMedia(playlistUrl: String, offsetTime: Long, duration: Long?) {
        enqueueDownloadRequest(
            DownloadRequest(
                inputMedias = arrayOf(
                    InputMedia(
                        content = playlistUrl,
                        type = DownloadMediaType.REMOTE_MEDIA
                    )
                ),
                dashOptions = DashOptions(offsetTime, duration),
                flags = DownloadRequest.Flags.DASH_PLAYLIST
            )
        )
    }

    fun downloadSingleMedia(
        mediaData: String,
        mediaType: DownloadMediaType,
        encryption: MediaEncryptionKeyPair? = null,
        attachmentType: AttachmentType? = null
    ) {
        enqueueDownloadRequest(
            DownloadRequest(
                inputMedias = arrayOf(
                    InputMedia(
                        content = mediaData,
                        type = mediaType,
                        encryption = encryption,
                        attachmentType = attachmentType?.name
                    )
                )
            )
        )
    }

    fun downloadMediaWithOverlay(
        original: InputMedia,
        overlay: InputMedia,
    ) {
        enqueueDownloadRequest(
            DownloadRequest(
                inputMedias = arrayOf(original, overlay),
                flags = DownloadRequest.Flags.MERGE_OVERLAY
            )
        )
    }

    fun downloadStream(
        streamUrl: String,
        audioStreamFormat: AudioStreamFormat
    ) {
        enqueueDownloadRequest(
            DownloadRequest(
                inputMedias = arrayOf(
                    InputMedia(
                        content = streamUrl,
                        type = DownloadMediaType.REMOTE_MEDIA
                    )
                ),
                flags = DownloadRequest.Flags.AUDIO_STREAM,
                audioStreamFormat = audioStreamFormat
            )
        )
    }
}