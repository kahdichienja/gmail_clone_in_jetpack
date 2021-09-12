package com.kchienja.learning.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.kchienja.learning.JetMailAttachmentChip
import com.kchienja.learning.R

const val EXPAND_ANIMATION_DURATION = 300
const val COLLAPSE_ANIMATION_DURATION = 300
const val FADE_IN_ANIMATION_DURATION = 350
const val FADE_OUT_ANIMATION_DURATION = 300


@Composable
fun GetAttachmentIcon(ext: String?, fileName: String) {
    when (ext) {
        "docx" -> JetMailAttachmentChip(attachment = R.drawable.ic_docx, text = fileName)
        "doc" -> JetMailAttachmentChip(attachment = R.drawable.ic_attachment_doc, text = fileName)
        "png" ->JetMailAttachmentChip(attachment =  R.drawable.ic_attachment_image, text = fileName)
        "jpg" -> JetMailAttachmentChip(attachment = R.drawable.ic_attachment_image, text = fileName)
        "jpeg" ->JetMailAttachmentChip(attachment =  R.drawable.ic_attachment_image, text = fileName)
        "bmp" -> JetMailAttachmentChip(attachment = R.drawable.ic_attachment_image, text = fileName)
        "gif" ->JetMailAttachmentChip(attachment =  R.drawable.ic_attachment_image, text = fileName)
    }
}