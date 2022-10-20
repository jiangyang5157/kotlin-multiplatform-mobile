package com.gmail.jiangyang5157.common.util

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Created by Yang Jiang on May 19, 2019
 */
object PermissionUtils {

    fun checkPermission(
        activity: FragmentActivity,
        permission: String,
        requestCode: Int,
        success: () -> Unit,
        failed: () -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                failed()
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        } else {
            success()
        }
    }

    fun neverShowAgainDialog(
        context: Context,
        message: Int,
        btnPositiveText: Int,
        btnNegativeText: Int,
        btnNegativeOnClickListener: DialogInterface.OnClickListener? = null
    ): AlertDialog.Builder {
        return AlertDialog.Builder(context).setMessage(context.getString(message))
            .setPositiveButton(context.getString(btnPositiveText)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivity(intent)
            }
            .setNegativeButton(context.getString(btnNegativeText), btnNegativeOnClickListener)
    }
}
