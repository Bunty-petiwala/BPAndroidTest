package com.comnet.androidtest.application

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.comnet.androidtest.R
import com.comnet.androidtest.utils.NetworkUtils
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    private var mInternetCheckCount = 0

    object NetworkState {
        var mIsInternet: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleNetworkChanges()
    }


    /*
   * Check internet connectivity availability
   * */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(
                this,
                { isConnected ->
                    NetworkState.mIsInternet = isConnected

                    if (!isConnected) {
                        val networkStatusLayout: Snackbar =
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.text_no_connectivity),
                                        BaseTransientBottomBar.LENGTH_LONG
                                )
                        val layoutView: View = networkStatusLayout.view
                        val params = layoutView.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.BOTTOM
                        layoutView.layoutParams = params
                        layoutView.background = ContextCompat.getDrawable(this, R.color.colorStatusNotConnected)
                        networkStatusLayout.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

                        networkStatusLayout.show()

                    } else {
                        if (mInternetCheckCount > 0) {
                            val networkStatusLayout: Snackbar =
                                    Snackbar.make(
                                            findViewById(android.R.id.content),
                                            getString(R.string.text_connectivity),
                                            BaseTransientBottomBar.LENGTH_LONG
                                    )
                            val layoutView: View = networkStatusLayout.view
                            val params = layoutView.layoutParams as FrameLayout.LayoutParams
                            params.gravity = Gravity.BOTTOM
                            layoutView.layoutParams = params
                            layoutView.background = ContextCompat.getDrawable(this, R.color.colorStatusConnected)
                            networkStatusLayout.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

                            networkStatusLayout.show()
                        }
                        mInternetCheckCount += 1
                    }

                }
        )
    }

}