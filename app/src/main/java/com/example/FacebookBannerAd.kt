package com.example

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.ads.AdSize
import com.facebook.ads.AdView

@Composable
fun FacebookBannerAd(
    placementId: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val adView = AdView(context, placementId, AdSize.BANNER_HEIGHT_50)
            adView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            adView.loadAd()
            adView
        },
        update = { adView ->
            // Handle updates if needed
        }
    )
}
