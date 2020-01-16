package team.deepvision.webviewfix.data

data class SSchoolHighlight(

    var color: HighlightingColor,
    var selectedText: String,
    var dayId: String,
    var componentId: String,
    var index: String,
    var length: String

) {

    enum class HighlightingColor(var argb: String, var title: String, constVal: Int) {

        NOT_SELECTED("#000000", "Transparent", 0),
        RED("#D33535", "Red", 1),
        ORANGE("#F09535", "Orange", 2),
        YELLOW("#E9C30B", "Yellow", 3),
        GREEN("#94DC3C", "Green", 4),
        CYAN("#44E3B9", "Cyan", 5),
        BLUE("#33ADD8", "Blue", 6),
        DARK_BLUE("#3347E4", "Dark Blue", 7),
        VIOLET("#BC35E0", "Violet", 8)

    }

}
