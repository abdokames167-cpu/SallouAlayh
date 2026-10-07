package com.salloualayh.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UserPresentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_USER_PRESENT) {
            // Hook for the floating reminder scheduler.
            // The production version will enforce the user's chosen daily limit
            // and rotate short authentic adhkar without repeating consecutively.
        }
    }
}
