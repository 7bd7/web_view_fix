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
            val listOfSelections = getHighlights()
            listOfSelections.add(highlight)
            putString(key, gson.toJson(listOfSelections))
            getHighlights()
            apply()
        }
    }

    fun getHighlights(): ArrayList<SSchoolHighlight> = gson.fromJson(prefs.getString(key, gson.toJson(emptyList<SSchoolHighlight>())), (object : TypeToken<List<SSchoolHighlight?>?>() {}.type))

    private inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

}