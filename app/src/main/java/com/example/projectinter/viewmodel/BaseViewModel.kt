package com.chipmong.dms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
open class BaseViewModel : ViewModel() {
    val disposable = CompositeDisposable()
    var isLoading  = MutableLiveData<Boolean>()



    open fun retrieveData(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}