package com.example.projectinter.callback

import kotlinx.coroutines.*


abstract class InternetDetectCoroutine<I, O>{
    var result: O? = null
    //private var result: O
    open fun onPreExecute() {}

    open fun onPostExecute(result: O?) {}
    abstract fun doInBackground(vararg params: I): O

    open fun <T> execute(vararg input: I) {
        GlobalScope.launch(Dispatchers.Main) {
            onPreExecute()
            callAsync(*input)
        }
    }

    private suspend fun callAsync(vararg input: I) {
        withContext(Dispatchers.IO) {
            result = doInBackground(*input)
        }
        GlobalScope.launch(Dispatchers.Main) {

            onPostExecute(result)


        }
    }

}