package com.example.memoreal_prototype.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_table",
    indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="first_name")
    var firstName:String? = null,
    @ColumnInfo(name="last_name")
    var lastName:String? = null,
    @ColumnInfo(name="middle_initial")
    var mi:String? = null,
    @ColumnInfo(name="username")
    var username:String,
    @ColumnInfo(name="password")
    var password:String,
    @ColumnInfo(name="contact_number")
    var contactNumber:String? = null,
    @ColumnInfo(name="email")
    var email:String,
    @ColumnInfo(name="birthdate")
    var birthDate:String? = null,
    @ColumnInfo(name="picture")
    var picture:String? = null
)

@Entity(
    tableName = "obituary_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]/*,
         onDelete = ForeignKey.CASCADE
         (kani siya kay mo delete sa uban tables if deleted ni siya)*/)
    ]
)
data class Obituary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="biography")
    var biography:String,
    @ColumnInfo(name="obituary_name")
    var obituaryName:String,
    @ColumnInfo(name="date_of_birth")
    var dateOfBirth:String,
    @ColumnInfo(name="date_of_death")
    var dateOfDeath:String,
    @ColumnInfo(name="key_events")
    var keyEvents:String,
    @ColumnInfo(name="picture")
    var picture:String? = null,
    @ColumnInfo(name="achievements")
    var achievements:String? = null,
    @ColumnInfo(name="favorite_quotes")
    var favoriteQuotes:String? = null,
    @ColumnInfo(name="creation_date")
    var creationDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name="last_modified")
    var lastModified: Long
)
@Entity(
    tableName = "payment_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class Payment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="payment_id")
    var paymentID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="amount")
    var amount:Double,
    @ColumnInfo(name="payment_date")
    var paymentDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name="payment_method")
    var paymentMethod:String,
    @ColumnInfo(name="status")
    var status:String
)

@Entity(
    tableName = "virtual_guestbook_table",
    foreignKeys = [
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class VirtualGuestbook(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="guestbook_id")
    var guestbookID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="guest_name")
    var guestName:String,
    @ColumnInfo(name="message")
    var message:String,
    @ColumnInfo(name="posting_date")
    var postingDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "notification_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"])
    ]
)
data class Notification(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="notification_id")
    var notificationID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="notification_type")
    var notificationType:String,
    @ColumnInfo(name="notification_content")
    var notificationContent:String,
    @ColumnInfo(name="notification_date")
    var notificationDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "subscription_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Payment::class, parentColumns = ["paymentID"], childColumns = ["paymentID"])
    ]
)
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="subscription_id")
    var subscriptionID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="payment_id")
    var paymentID:Int,
    @ColumnInfo(name="plan_type")
    var planType:String,
    @ColumnInfo(name="plan_amount")
    var planAmount:Double,
    @ColumnInfo(name="start_date")
    var startDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name="end_date")
    var endDate: Long,
    @ColumnInfo(name="status")
    var status:String,
    @ColumnInfo(name="features")
    var features:String? = null,
    @ColumnInfo(name="auto_renewal")
    var autoRenewal:Boolean,
    @ColumnInfo(name="renewal_date")
    var renewalDate: Long
)

@Entity(
    tableName = "user_interaction_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class UserInteraction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="interaction_id")
    var interactionID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="interaction_type")
    var interactionType:String,
    @ColumnInfo(name="interaction_date")
    var interactionDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "donation_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class Donation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="donation_id")
    var donationID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="donation_type")
    var donationType:String,
    @ColumnInfo(name="amount")
    var amount:Double,
    @ColumnInfo(name="donation_date")
    var donationDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name="message")
    var message:String? = null
)

@Entity(
    tableName = "gallery_table",
    foreignKeys = [
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class Gallery(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="gallery_id")
    var galleryID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="gallery_name")
    var galleryName:String,
    @ColumnInfo(name="creation_date")
    var creationDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "gallery_media_table",
    foreignKeys = [
        ForeignKey(entity = Gallery::class, parentColumns = ["galleryID"], childColumns = ["galleryID"])
    ]
)
data class GalleryMedia(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="gallery_media_id")
    var galleryMediaID:Int,
    @ColumnInfo(name="gallery_id")
    var galleryID:Int,
    @ColumnInfo(name="media_type")
    var mediaType:String,
    @ColumnInfo(name="file_name")
    var fileName:String,
    @ColumnInfo(name="upload_date")
    var uploadDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "text_entry_table",
    foreignKeys = [
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class TextEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="text_entry_id")
    var textEntryID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="entry_type")
    var entryType:String,
    @ColumnInfo(name="entry_content")
    var entryContent:String,
    @ColumnInfo(name="entry_date")
    var entryDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "ar_session_table",
    foreignKeys = [
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class ARSession(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ar_session_id")
    var arSessionID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="session_name")
    var sessionName:String,
    @ColumnInfo(name="session_start_time")
    var sessionStartTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name="session_end_time")
    var sessionEndTime: Long,
    @ColumnInfo(name="device_type")
    var deviceType:String,
    @ColumnInfo(name="tracking_mode")
    var trackingMode:String,
    @ColumnInfo(name="frame_rate")
    var frameRate:Int,
    @ColumnInfo(name="match_frame_rate_enabled")
    var matchFrameRateEnabled:Boolean
)

@Entity(
    tableName = "ar_media_table",
    foreignKeys = [
        ForeignKey(entity = ARSession::class, parentColumns = ["arSessionID"], childColumns = ["arSessionID"])
    ]
)
data class ARMedia(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ar_media_id")
    var arMediaID:Int,
    @ColumnInfo(name="ar_session_id")
    var arSessionID:Int,
    @ColumnInfo(name="media_type")
    var mediaType:String,
    @ColumnInfo(name="file_name")
    var fileName:String,
    @ColumnInfo(name="upload_date")
    var uploadDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "ar_ai_table",
    foreignKeys = [
        ForeignKey(entity = ARSession::class, parentColumns = ["arSessionID"], childColumns = ["arSessionID"]),
        ForeignKey(entity = ARMedia::class, parentColumns = ["arMediaID"], childColumns = ["arMediaID"])
    ]
)
data class ARAI(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ar_ai_id")
    var arAIID:Int,
    @ColumnInfo(name="ar_session_id")
    var arSessionID:Int,
    @ColumnInfo(name="preference_id")
    var preferenceID:Int,
    @ColumnInfo(name="ar_media_id")
    var arMediaID:Int,
    @ColumnInfo(name="ar_description")
    var arDescription:String
)

@Entity(
    tableName = "preferences_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"])
    ]
)
data class Preferences(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="preference_id")
    var preferenceID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="theme_pref")
    var themePref:String,
    @ColumnInfo(name="font_size_pref")
    var fontSizePref:String,
    @ColumnInfo(name="language_pref")
    var languagePref:String,
    @ColumnInfo(name="interaction_mode")
    var interactionMode:String,
    @ColumnInfo(name="accessibility_options")
    var accessibilityOptions:String? = null,
    @ColumnInfo(name="registration_date")
    var registrationDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "privacy_settings_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class PrivacySettings(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="privacy_settings_id")
    var privacySettingsID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="visibility")
    var visibility:String,
    @ColumnInfo(name="last_modified_by")
    var lastModifiedBy:String,
    @ColumnInfo(name="last_modified_date")
    var lastModifiedDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "guest_user_table",
    foreignKeys = [
        ForeignKey(entity = Obituary::class, parentColumns = ["obituaryID"], childColumns = ["obituaryID"])
    ]
)
data class GuestUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="guest_user_id")
    var guestUserID:Int,
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="guest_name")
    var guestName:String,
    @ColumnInfo(name="action")
    var action:String
)
