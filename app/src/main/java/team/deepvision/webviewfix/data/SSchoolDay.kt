package team.deepvision.webviewfix.data

data class SSchoolDay(

    val id: String,

    val lessonId: String,

    val title: String,

    val date: String,

    val coverUrl: String,

    val content: String,

    val userData: SSchoolDayUserData

)
