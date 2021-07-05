package com.example.projectinter.utils

import android.util.Log
import androidx.annotation.Nullable
import com.chipmong.dms.utils.NetworkUtils
import com.example.projectinter.callback.InternetDetectCoroutine

abstract class InternetCheckCoroutine(private var onDetectInternetFinished: ((Boolean?) -> (Unit))? = null)  {}