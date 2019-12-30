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

        NOT_SELECTED("#00000000", "Transparent", 0),
        RED("#FFD33535", "Red", 1),
        ORANGE("#FFF09535", "Orange", 2),
        YELLOW("#FFE9C30B", "Yellow", 3),
        GREEN("#FF94DC3C", "Green", 4),
        CYAN("#FF44E3B9", "Cyan", 5),
        BLUE("#FF33ADD8", "Blue", 6),
        DARK_BLUE("#FF3347E4", "Dark Blue", 7),
        VIOLET("#FFBC35E0", "Violet", 8)

    }

}
