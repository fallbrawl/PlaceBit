package com.attractgroup.bluemoon.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class User(
    @SerializedName("company_phone")
    var companyPhone: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("terms_accepted")
    var termsAccepted: Int = 1,
    @SerializedName("terms_accepted_at")
    var termsAcceptedAt: Int = 0,
    @SerializedName("created_at")
    var createdAt: Int = 0,
    @SerializedName("email_verified_at")
    var emailVerifiedAt: Int = 0,
    @SerializedName("hand_based_verification_status")
    var handBasedVerificationStatus: Int = 0,
    @SerializedName("company_number")
    var companyNumber: String? = null,
    @SerializedName("updated_at")
    var updatedAt: Int = 0,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("permissions")
    var permissions: RealmList<String>? = null,
    @SerializedName("company_name")
    var companyName: String? = null,
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("country_id")
    var countryId: Int = 0,
    @SerializedName("email")
    var email: String? = null,
    var password: String? = null,
    @SerializedName("password_confirmation")
    var passwordConfirmation: String? = password,
    @SerializedName("user_type")
    var userType: Int = 0,
//    @Ignore
//    @SerializedName("relations")
//    var relations: Relations? = null,
    var balance: Float? = null,
    @Expose
    var balanceCurrency: String = ""
) : RealmObject()