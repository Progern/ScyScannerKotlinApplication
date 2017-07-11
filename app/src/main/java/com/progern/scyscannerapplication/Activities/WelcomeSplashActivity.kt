package com.progern.scyscannerapplication.Activities

import com.progern.scyscannerapplication.R
import com.stephentuso.welcome.BasicPage
import com.stephentuso.welcome.WelcomeActivity
import com.stephentuso.welcome.WelcomeConfiguration

/**
 * Created by Oleh Misko on 7/11/17.
 */

class WelcomeSplashActivity : WelcomeActivity() {

    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
                .page(BasicPage(R.drawable.ic_aeroplane,
                        "Find cheap flights",
                        resources.getString(R.string.lorem_ipsum))
                        .background(R.color.primary_dark)
                )
                .page(BasicPage(R.drawable.ic_hotel,
                        "Book a hotel",
                        resources.getString(R.string.lorem_ipsum))
                        .background(R.color.primary)
                )
                .page(BasicPage(R.drawable.ic_car,
                        "Rent a car",
                        resources.getString(R.string.lorem_ipsum))
                        .background(R.color.primary_dark)
                )
                .swipeToDismiss(true)
                .build()
    }

}