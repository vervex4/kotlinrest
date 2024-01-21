package com.jeenatech.platform.ecommerce.usermanagement

import aws.sdk.kotlin.services.cognitoidentityprovider.model.*
import aws.sdk.kotlin.services.cognitoidentityprovider.CognitoIdentityProviderClient
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class AwsCognitoServiceClient {

    suspend fun signUp(userNameVal: String, passwordVal: String?, emailVal: String?) {
        val userAttrs = AttributeType {
            name = "email"
            value = emailVal
        }

        val clientIdVal="5nimibbv5cfcenhgt3dga8bg4"
        val secretKey="nhldbf33rvfjh06ret3b1o2024qrnslpbe6npust6nr84sqoklv"
        val userAttrsList = mutableListOf<AttributeType>()
        userAttrsList.add(userAttrs)
        val secretVal = calculateSecretHash(clientIdVal, secretKey, userNameVal)
        val signUpRequest = SignUpRequest {
            userAttributes = userAttrsList
            username = userNameVal
            clientId = clientIdVal
            password = passwordVal
            secretHash = secretVal
        }

        try {
            CognitoIdentityProviderClient { region = "us-east-2" }.use { identityProviderClient ->
                identityProviderClient.signUp(signUpRequest)
                println("User has been signed up")
            }
        }
        catch (e:Exception) {
            println("what went wrong bro!!!" + e.message)
        }


    }
    fun calculateSecretHash(userPoolClientId: String, userPoolClientSecret: String, userName: String): String {
        val macSha256Algorithm = "HmacSHA256"
        val signingKey = SecretKeySpec(
            userPoolClientSecret.toByteArray(StandardCharsets.UTF_8),
            macSha256Algorithm
        )
        try {
            val mac = Mac.getInstance(macSha256Algorithm)
            mac.init(signingKey)
            mac.update(userName.toByteArray(StandardCharsets.UTF_8))
            val rawHmac = mac.doFinal(userPoolClientId.toByteArray(StandardCharsets.UTF_8))
            return Base64.getEncoder().encodeToString(rawHmac)
        } catch (e: UnsupportedEncodingException) {
            println(e.message)
        }
        return ""
    }


}