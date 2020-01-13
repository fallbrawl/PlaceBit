

import com.attractgroup.bluemoon.data.model.User


object UserPrefs : PrefManager("global_user") {

    @JvmStatic
    var username by StringPref()

    @JvmStatic
    var authToken by StringPref()

    @JvmStatic
    var refreshToken by StringPref()

    @JvmStatic
    var firebaseToken by StringPref()

    @JvmStatic
    var userId by LongPref()

    @JvmStatic
    var user by GsonPref(User::class.java)

    @JvmStatic
    var refId by IntPref()
}