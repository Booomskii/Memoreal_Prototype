package com.example.memoreal_prototype.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update (onConflict = OnConflictStrategy.ABORT)
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE LOWER(username) = LOWER(:username))")
    suspend fun checkUsername(username: String): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE email = :email)")
    suspend fun checkEmail(email: String): Boolean

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    //for test
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'user_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface ObituaryDao{

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertObituary(obituary: Obituary): Long

    @Update (onConflict = OnConflictStrategy.ABORT)
    suspend fun updateObituary(obituary: Obituary): Int

    @Delete
    suspend fun deleteObituary(obituary: Obituary): Int

    @Query("SELECT * FROM obituary_table")
    fun getAllObituaries(): LiveData<List<User>>

    //for test
    @Query("DELETE FROM obituary_table")
    suspend fun deleteAllUsers()
    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'obituary_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface FamilyDao{

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertObituary(obituary: Obituary): Long

    @Update (onConflict = OnConflictStrategy.ABORT)
    suspend fun updateObituary(obituary: Obituary): Int

    @Delete
    suspend fun deleteObituary(obituary: Obituary): Int

    @Query("SELECT * FROM obituary_table")
    fun getAllObituaries(): LiveData<List<User>>

    //for test
    @Query("DELETE FROM obituary_table")
    suspend fun deleteAllUsers()
    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'obituary_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPayment(payment: Payment): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updatePayment(payment: Payment): Int

    // Murag dli na kailangan kung gusto mo record ang transaction
    @Query("SELECT * FROM payment_table")
    fun getAllPayments(): LiveData<List<Payment>>
}


@Dao
interface VirtualGuestbookDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertVirtualGuestbook(virtualGuestbook: VirtualGuestbook): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateVirtualGuestbook(virtualGuestbook: VirtualGuestbook): Int

    @Delete
    suspend fun deleteVirtualGuestbook(virtualGuestbook: VirtualGuestbook): Int

    @Query("SELECT * FROM virtual_guestbook_table")
    fun getAllVirtualGuestbooks(): LiveData<List<VirtualGuestbook>>

    // For test
    @Query("DELETE FROM virtual_guestbook_table")
    suspend fun deleteAllVirtualGuestbooks()

    // Reset primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'virtual_guestbook_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface UserInteractionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserInteraction(userInteraction: UserInteraction): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateUserInteraction(userInteraction: UserInteraction): Int

    @Delete
    suspend fun deleteUserInteraction(userInteraction: UserInteraction): Int

    @Query("SELECT * FROM user_interaction_table")
    fun getAllUserInteractions(): LiveData<List<UserInteraction>>

    //for test
    @Query("DELETE FROM user_interaction_table")
    suspend fun deleteAllUserInteractions()

    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'user_interaction_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertGallery(gallery: Gallery): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateGallery(gallery: Gallery): Int

    @Delete
    suspend fun deleteGallery(gallery: Gallery): Int

    @Query("SELECT * FROM gallery_table")
    fun getAllGalleries(): LiveData<List<Gallery>>

    //for test
    @Query("DELETE FROM gallery_table")
    suspend fun deleteAllGalleries()

    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'gallery_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNotification(notification: Notification): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateNotification(notification: Notification): Int

    @Delete
    suspend fun deleteNotification(notification: Notification): Int

    @Query("SELECT * FROM notification_table")
    fun getAllNotifications(): LiveData<List<Notification>>

    // For test
    @Query("DELETE FROM notification_table")
    suspend fun deleteAllNotifications()

    // Reset primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'notification_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSubscription(subscription: Subscription): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateSubscription(subscription: Subscription): Int

    // No delete if keeping subscription history
    @Query("SELECT * FROM subscription_table")
    fun getAllSubscriptions(): LiveData<List<Subscription>>
}

/*@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMedia(media: Media): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateMedia(media: Media): Int

    @Delete
    suspend fun deleteMedia(media: Media): Int

    @Query("SELECT * FROM media_table")
    fun getAllMedia(): LiveData<List<Media>>

    // For test
    @Query("DELETE FROM media_table")
    suspend fun deleteAllMedia()

    // Reset primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'media_table'")
    suspend fun resetPrimaryKey()
}*/

@Dao
interface TextEntryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTextEntry(textEntry: TextEntry): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTextEntry(textEntry: TextEntry): Int

    @Delete
    suspend fun deleteTextEntry(textEntry: TextEntry): Int

    @Query("SELECT * FROM text_entry_table")
    fun getAllTextEntries(): LiveData<List<TextEntry>>
}

@Dao
interface ARSessionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertARSession(arSession: ARSession): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateARSession(arSession: ARSession): Int

    @Delete
    suspend fun deleteARSession(arSession: ARSession): Int

    @Query("SELECT * FROM ar_session_table")
    fun getAllARSessions(): LiveData<List<ARSession>>
}

@Dao
interface ARMediaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertARMedia(arMedia: ARMedia): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateARMedia(arMedia: ARMedia): Int

    @Delete
    suspend fun deleteARMedia(arMedia: ARMedia): Int

    @Query("SELECT * FROM ar_media_table")
    fun getAllARMedia(): LiveData<List<ARMedia>>
}

@Dao
interface ARAIDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertARAI(arAI: ARAI): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateARAI(arAI: ARAI): Int

    @Delete
    suspend fun deleteARAI(arAI: ARAI): Int

    @Query("SELECT * FROM ar_ai_table")
    fun getAllARAIs(): LiveData<List<ARAI>>
}

@Dao
interface PreferencesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPreferences(preferences: Preferences): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updatePreferences(preferences: Preferences): Int

    @Delete
    suspend fun deletePreferences(preferences: Preferences): Int

    @Query("SELECT * FROM preferences_table")
    fun getAllPreferences(): LiveData<List<Preferences>>

    // For test
    @Query("DELETE FROM preferences_table")
    suspend fun deleteAllPreferences()

    // Reset primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'preferences_table'")
    suspend fun resetPrimaryKey()
}

@Dao
interface PrivacySettingsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPrivacySettings(privacySettings: PrivacySettings): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updatePrivacySettings(privacySettings: PrivacySettings): Int

    @Delete
    suspend fun deletePrivacySettings(privacySettings: PrivacySettings): Int

    @Query("SELECT * FROM privacy_settings_table")
    fun getAllPrivacySettings(): LiveData<List<PrivacySettings>>

    // For test
    @Query("DELETE FROM privacy_settings_table")
    suspend fun deleteAllPrivacySettings()

    // Reset primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'privacy_settings_table'")
    suspend fun resetPrimaryKey()
}
