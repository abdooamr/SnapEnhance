package me.rhunk.abdooenhance.core.messaging

import me.rhunk.abdooenhance.common.data.ContentType

class ExportParams(
    val exportFormat: ExportFormat = ExportFormat.HTML,
    val messageTypeFilter: List<ContentType>? = null,
    val amountOfMessages: Int? = null,
    val downloadMedias: Boolean = false,
)
