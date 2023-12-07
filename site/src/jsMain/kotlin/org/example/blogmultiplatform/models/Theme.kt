package org.example.blogmultiplatform.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class Theme(
    val hex: String,
    val rbg: CSSColorValue
) {
    Primary(
        hex = "#00A2FF",
        rbg = rgb(0, 162, 255)
    )
,
    LightGray(
    hex = "#FAFAFA",
    rbg = rgb(250, 250, 250)
    )
}