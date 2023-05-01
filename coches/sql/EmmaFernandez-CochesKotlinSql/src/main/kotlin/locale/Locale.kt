package locale

import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private val locale = Locale.getDefault()
private val lang = locale.displayLanguage
private val country = locale.displayCountry

fun LocalDate.toLocalDate(): String = this.format(
    DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(locale)
)

fun LocalDateTime.toLocalDateTime(): String = this.format(
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(locale)
)

fun Double.toLocalMoney(): String = NumberFormat
    .getCurrencyInstance(locale)
    .format(this)
