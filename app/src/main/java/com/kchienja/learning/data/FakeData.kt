package com.kchienja.learning.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.mutableStateListOf
import com.kchienja.learning.R
import com.kchienja.learning.model.AttachmentFile
import com.kchienja.learning.model.MailModel


var mails =  mutableStateListOf(
    MailModel(
        id = 1,
        title = "Linked In Job Alerts",
        subject = "3, jobs for 'mobile engineer",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago,Illinois, ",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.lin,
        isImportant = true,
        hasAttachment = false,
    ),
    MailModel(
        id = 2,
        title = "Linked In Job Alerts",
        subject = "3, jobs for 'mobile engineer",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago, United ",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.lin,
        isImportant = false,
        hasAttachment = true,
        attachmentFiles = mutableStateListOf(
            AttachmentFile(
                fileName = "list of jobs",
                ext = "docx"
            ),
            AttachmentFile(
                fileName = "Screenshots",
                ext = "png"
            ),
            AttachmentFile(
                fileName = "Cities",
                ext = "png"
            ),
            AttachmentFile(
                fileName = "Screenshots",
                ext = "png"
            ),
        ),
    ),
    MailModel(
        id= 3,
        title = "Course Hero",
        subject = "You file Uploads",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago Illinois,",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.hero,
        isImportant = true,
        hasAttachment = true,
        attachmentFiles = mutableStateListOf(
            AttachmentFile(
                fileName = "list of jobs",
                ext = "docx"
            )
        ),
    ),
    MailModel(
        id= 4,
        title = "Google Play Support",
        subject = "Re: Your message about Google Play [4-6392000030990]\n",
        text = "Hi Developer,\n" +
                "\n" +
                "Thanks for contacting Google Play Developer Support.\n" +
                "App versions have to be at least one number higher than the previous version, so an app at version 999999999 must be replaced by a version that is at least 1000000000. Apps generally start at low version numbers, such as the first version at 1, the second version 2, and so on.\n" +
                "\n" +
                "Having app versions numbering over 2100010000 is highly unusual and indicates that there are over two billion APK versions, after which point the app may become unstable. The error you are seeing indicates that the console is forcibly limiting the number of APK uploads for your app, as implied by the version number.\n" +
                "\n" +
                "As this is a system limitation, it cannot be overridden. It is also not possible to downgrade the version of your app to a lower number. The only option is to publish a new app under a new package name and restart your app versioning at a lower number. In order to transition existing users to the new app, you’ll need to update the original app’s description with a link to the new app and unpublish the original app.\n" +
                "\n" +
                "I understand this is a significant inconvenience and I appreciate your understanding.\n" +
                "\n" +
                "You can read more about app versioning in the Android Studio Help Center.\n" +
                "\n" +
                "Thanks for supporting Google Play, and please let me know if you have any other questions or need more clarification.\n" +
                "\n" +
                "Regards,\n" +
                "Jennifer\n" +
                "Google Play Developer Support\n" +
                "Did you know we offer chat support in English? You can chat with us Monday through Friday, 12 a.m. to 12 a.m. Greenwich Time (GMT).",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.google,
        isImportant = true,
        hasAttachment = false,
    ),
)
