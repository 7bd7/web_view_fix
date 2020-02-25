package team.deepvision.webviewfix.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Repo(context: Context) {

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val key = "HIGHLIGHTINGS_SHAR_PREFS_KEY"
    val gson = Gson()

    fun saveHighlighting(highlight: SSchoolHighlight) {
        prefs.edit {
            val listOfSelections = getAllHighlights()
            listOfSelections.add(highlight)
            putString(key, gson.toJson(listOfSelections))
            apply()
        }
    }

    fun getAllHighlights(): ArrayList<SSchoolHighlight> = gson.fromJson(prefs.getString(key, gson.toJson(emptyList<SSchoolHighlight>())), (object : TypeToken<List<SSchoolHighlight?>?>() {}.type))

    fun getHighlight(id: String): SSchoolHighlight {
        return getAllHighlights().first { it.id == id }
    }

    fun deleteHighlighting(highlight: SSchoolHighlight) {
        prefs.edit {
            val listOfSelections = getAllHighlights()
            listOfSelections.remove(highlight)
            putString(key, gson.toJson(listOfSelections))
            apply()
        }
    }

}
