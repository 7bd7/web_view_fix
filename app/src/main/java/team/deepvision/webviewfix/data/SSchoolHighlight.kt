package team.deepvision.webviewfix.data

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class SSchoolHighlight(

    var selectedText: String,
    var componentId: String,
    var index: String,
    var length: String,
    var color: HighlightingColor,
    var dayId: String

) {

    enum class HighlightingColor(

        @SerializedName("rgb")
        var rgb: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("constVal")
        var constVal: Int

    ) {

        NOT_SELECTED("#000000", "Transparent", 0),
        RED("#D33535", "Red", 1),
        ORANGE("#F09535", "Orange", 2),
        YELLOW("#E9C30B", "Yellow", 3),
        GREEN("#94DC3C", "Green", 4),
        CYAN("#44E3B9", "Cyan", 5),
        BLUE("#33ADD8", "Blue", 6),
        DARK_BLUE("#3347E4", "Dark Blue", 7),
        VIOLET("#BC35E0", "Violet", 8);

        class Deserializer : JsonDeserializer<HighlightingColor?> {
            @Throws(JsonParseException::class)
            override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): HighlightingColor? {
                val values: Array<HighlightingColor> = HighlightingColor.values()
                for (value in values) {
                    if (json.asString.contains(value.rgb)) return value
                }
                return null
            }
        }

        class Serializer : JsonSerializer<HighlightingColor?> {
            override fun serialize(
                src: HighlightingColor?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement {
                return JsonPrimitive(src?.rgb)
            }

        }


    }

}
