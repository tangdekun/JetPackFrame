/*
 * Copyright 2014-2017 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.djt.base.crash

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.djt.base.R


class DefaultErrorActivity : AppCompatActivity() {

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This is needed to avoid a crash if the developer has not specified
        //an app-level theme that extends Theme.AppCompat
        val a = obtainStyledAttributes(R.styleable.AppCompatTheme)
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar)
        }
        a.recycle()

        setContentView(R.layout.customactivityoncrash_default_error_activity)

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        val restartButton = findViewById<Button>(R.id.customactivityoncrash_error_activity_restart_button)

        val config = CustomActivityOnCrash.getConfigFromIntent(intent)

        if (config.isShowRestartButton && config.restartActivityClass != null) {
            restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app)
            restartButton.setOnClickListener { CustomActivityOnCrash.restartApplication(this@DefaultErrorActivity, config) }
        } else {
            restartButton.setOnClickListener { CustomActivityOnCrash.closeApplication(this@DefaultErrorActivity, config) }
        }

        val moreInfoButton = findViewById<Button>(R.id.customactivityoncrash_error_activity_more_info_button)

        if (config.isShowErrorDetails) {
            moreInfoButton.setOnClickListener {
                //We retrieve all the error data and show it

                val dialog = AlertDialog.Builder(this@DefaultErrorActivity)
                    .setTitle(R.string.customactivityoncrash_error_activity_error_details_title)
                    .setMessage(
                        CustomActivityOnCrash.getAllErrorDetailsFromIntent(
                            this@DefaultErrorActivity,
                            intent
                        )
                    )
                    .setPositiveButton(R.string.customactivityoncrash_error_activity_error_details_close, null)
                    .setNeutralButton(R.string.customactivityoncrash_error_activity_error_details_copy
                    ) { _, _ ->
                        copyErrorToClipboard()
                        Toast.makeText(
                            this@DefaultErrorActivity,
                            R.string.customactivityoncrash_error_activity_error_details_copied,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
                val textView = dialog.findViewById(android.R.id.message) as TextView
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.customactivityoncrash_error_activity_error_details_text_size)
                )
            }
        } else {
            moreInfoButton.visibility = View.GONE
        }

        val defaultErrorActivityDrawableId = config.errorDrawable
        val errorImageView = findViewById<ImageView>(R.id.customactivityoncrash_error_activity_image)

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    defaultErrorActivityDrawableId,
                    theme
                )
            )
        }
    }

    private fun copyErrorToClipboard() {
        val errorInformation =
            CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent)

        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            getString(R.string.customactivityoncrash_error_activity_error_details_clipboard_label),
            errorInformation
        )
        clipboard.primaryClip = clip
    }
}
