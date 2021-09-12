package com.kchienja.learning.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class MailModel(
    val id: Int,
    val leading : Int,
    val subject: String,
    val hasAttachment: Boolean,
    val isImportant: Boolean,
    val text : String,
    val trailing: ImageVector,
    val title : String,
    val trailingText : String,
    val attachmentFiles: List<AttachmentFile> ? = null
)

data class AttachmentFile(
    val fileName: String,
    val ext: String,
)
