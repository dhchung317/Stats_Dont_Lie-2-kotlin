package com.hyunki.statsdontlie2.utils

/**
 * Last Updated - 9/11/19
 * Last Change By - Eric Diaz
 *
 *
 * A Utility class for getting player photos
 */
object PlayerUtil {
    private const val PLAYER_PHOTO_URL = "https://nba-players.herokuapp.com/players/"
    private const val DEFAULT_PLAYER_PHOTO_URL = "https://kytopen.com/wp-content/uploads/2018/01/placeholder_avatar-400x400.png"
    @kotlin.jvm.JvmStatic
    fun getPlayerPhotoUrl(firstName: String,
                          lastName: String): String {
        return if (isValidFullName(firstName, lastName)) createPlayerPhotoUrl(firstName, lastName) else DEFAULT_PLAYER_PHOTO_URL
    }

    /**
     * I used a switch here in case we run into another issue in the future
     * if decide to add more players.
     */
    private fun createPlayerPhotoUrl(firstName: String,
                                     lastName: String): String {
        return when (firstName) {
            "D'Angelo" -> "$PLAYER_PHOTO_URL$lastName/dangelo"
            else -> "$PLAYER_PHOTO_URL$lastName/$firstName"
        }
    }

    private fun isValidFullName(firstName: String,
                                lastName: String): Boolean {
        return firstName != null && lastName != null && firstName != "" && lastName != ""
    }
}