package com.kchienja.learning.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GetTextColor(): Color = if (isSystemInDarkTheme()) lPrimary else dPrimary


@Composable
fun GetStarColor(isStarred: Boolean = false): Color = if (isStarred) defaultOrangeGold else grayBlack

@Composable
fun GetAttachmentColor(): Color = if(isSystemInDarkTheme()) lAttachment else dAttachment
val  lPrimary = Color(0xFFFFFFFF)
val  lPrimaryVariant = Color(0xFFFFFFFF)
val  lSecondary = Color(0xFFFFFFFF)
val defaultRed = Color(0xFFFF0303)
val defaultOrangeGold = Color(0xFFFFC107)
val grayBlack = Color(0xB90A0A0A)
val dAttachment = Color(0xB94B4E64)
val lAttachment = Color(0xBF705454)
val grayBlackPrimary = Color(0xB9A79F9F)
val success = Color(0xFF07A051)
val selectedItemColor = Color(0x19FF0000)
val selectedItemColorText = Color(0xC6FF0000)
val  dPrimary =  Color(0xFF413E3E)
val  dDrawerPrimary =  Color(0x413E3E)
val  dPrimaryVariant =  Color(0xFFD1C9C9)
val  dSecondary  = Color(0xFFA8A5A5)
val jetmail1 = Color(0xFF050591)
val jetmail2 = Color(0xFF7A0202)
val jetmail3 = Color(0xFFFF9800)
val jetmail4 = Color(0xFF03205C)
val jetmail5 = Color(0xFF02832D)
val jetmail6 = Color(0xFFDB0C0C)
val jetmail7 = Color(0xFFF7E122)

val cardCollapsedBackgroundColor = Color(0xFFFEFFFD)
val cardExpandedBackgroundColor = Color(0xFFFFDA6D)