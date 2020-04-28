package com.example.vinid_icecreams.extension

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

// Realm object containing a RealmList with @Parcelize
// https://stackoverflow.com/a/49456435


fun Context.openPermissionSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun Fragment.isDeniedPermanently(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        shouldShowRequestPermissionRationale(permission).not()
    } else {
        false
    }
}

fun BigDecimal.format(separator: Char = '.'): String {
    val formatter = DecimalFormat.getInstance(Locale.US) as DecimalFormat
    formatter.decimalFormatSymbols.groupingSeparator = separator
    return formatter.format(this)
}

fun String?.orEmpty() : String = this ?: ""

fun Boolean?.orFalse(): Boolean = this ?: false

fun Double?.orMax(): Double = this ?: Double.MAX_VALUE

fun Int?.orZero() : Int = this ?: 0

internal fun Context.hasPermission(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun TextView.buildTextHighlightSpanAndClickable(
    text: String,
    highlightText: String,
    highlightTextColor: Int,
    clickableSpan: ClickableSpan?
) {
    val positionStart = text.indexOf(highlightText)
    val positionEnd = positionStart + highlightText.length

    val span = SpannableString(text)
    if (clickableSpan != null) {
        span.setSpan(clickableSpan, positionStart, positionEnd, 0)
    }
    span.setSpan(
        ForegroundColorSpan(highlightTextColor),
        positionStart,
        positionEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    movementMethod = LinkMovementMethod.getInstance()
    setText(span, TextView.BufferType.SPANNABLE)
    isSelected = true
}
