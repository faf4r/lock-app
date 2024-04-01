package com.tmf.lock

import android.content.Context
import android.widget.Toast

class ToastUtil constructor(context: Context) {
    private val context: Context
    private var toast: Toast?
    init {
        this.context = context
        this.toast = null
    }

     fun show(resId: Int) {
        if (toast==null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        } else {
            toast?.cancel()
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        }
        toast?.show()
    }
     fun show(text: String) {
        if (toast==null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast?.cancel()
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        }
        toast?.show()
    }
    fun destroy() {
        if (toast != null) {
            toast?.cancel()
        }
    }
}