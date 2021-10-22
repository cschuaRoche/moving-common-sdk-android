package com.roche.ssg.sample.auth.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AWSConfigDTO(
    @SerializedName("CognitoUserPool") val cognitoUserPool: CognitoUserPoolDTO,
    @SerializedName("IdentityManager") val identityManager: IdentityManagerDTO = IdentityManagerDTO()
) : Parcelable

@Parcelize
data class CognitoUserPoolDTO(
    @SerializedName("Default") val Default: CognitoUserPoolDTOImpl
) : Parcelable

@Parcelize
data class IdentityManagerDTO(
    @SerializedName("Default") val Default: IdentityManagerDTOImpl = IdentityManagerDTOImpl()
) : Parcelable

@Parcelize
data class CognitoUserPoolDTOImpl(
    @SerializedName("AppClientId") val appClientId: String,
    @SerializedName("PoolId") val poolId: String,
    @SerializedName("Region") val region: String
) : Parcelable

@Parcelize
class IdentityManagerDTOImpl : Parcelable