package com.sy.renz.bingo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    // on below line creating a variable for location.
    // on below line creating a column for our maps.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-5641019517069386/5474368121"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}