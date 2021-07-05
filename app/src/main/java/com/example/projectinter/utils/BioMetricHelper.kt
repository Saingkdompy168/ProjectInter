package com.chipmong.dms.utils

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BioMetricHelper {

//    fun checkBiometric(activity: FragmentActivity) {
//        if (DmsSharePreference.getInstance(activity).isEnableFingerPrint() &&
//            isBiometricAvailable(activity)
//        ) {
//            initBiometric(activity)
//        }
//    }

    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun initBiometric(activity: FragmentActivity, onCallback: ((Boolean) -> Unit) = {}) {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d("BioMetricHelper", "$errorCode :: $errString")
                onCallback.invoke(false)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d("BioMetricHelper", "Authentication failed")
                onCallback.invoke(false)
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d("BioMetricHelper", "Authentication was successful")
//                DmsSharePreference.getInstance(activity).enableFingerprint(true)
                onCallback.invoke(true)
            }
        }
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirm your fingerprint")
            .setDescription("")
            .setNegativeButtonText("Cancel")
//            .setDeviceCredentialAllowed(true)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}