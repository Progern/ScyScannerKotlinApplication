package com.progern.scyscannerapplication

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.progern.scyscannerapplication.Activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.contrib.NavigationViewActions
import android.view.View
import com.progern.scyscannerapplication.Utils.*
import org.hamcrest.Matcher



/**
 * Created by Oleh Misko on 7/12/17.
 * This is a test class for Navigation Drawer
 * to check wheter it correctly launches
 * activities
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {



    @Rule
    @JvmField
    var mAddNotesActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun openNavigationDrawer_startFlightsActivity() {
        openNavigationDrawer()
        clickOnNavigationDrawerItem(FLIGHTS_ID)

    }

    @Test
    @Throws(Exception::class)
    fun openNavigationDrawer_startCarsActivity() {
        openNavigationDrawer()
        clickOnNavigationDrawerItem(CARS_ID)
    }

    @Test
    @Throws(Exception::class)
    fun openNavigationDrawer_startHotelsActivity() {
        openNavigationDrawer()
        clickOnNavigationDrawerItem(HOTELS_ID)
    }

    @Test
    @Throws(Exception::class)
    fun openNavigationDrawer_startSettingsActivity(){
        openNavigationDrawer()
        clickOnNavigationDrawerItem(SETTINGS_ID)
    }

    @Test
    @Throws(Exception::class)
    fun openNavigationDrawer_startAboutActivity() {
        openNavigationDrawer()
        clickOnNavigationDrawerItem(ABOUT_ID)
    }

    fun openNavigationDrawer() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(actionOpenDrawer())
    }

    fun clickOnNavigationDrawerItem(id: Int) {

        when (id) {

            FLIGHTS_ID -> {
                Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.find_flight))
                Espresso.onView(ViewMatchers.withId(R.id.fab_find_flights)).check({ view, viewNotFoundException -> ViewMatchers.isDisplayed() })

            }

            CARS_ID -> {
                Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.rent_car))
                Espresso.onView(ViewMatchers.withId(R.id.fab_rent_cars)).check({ view, viewNotFoundException -> ViewMatchers.isDisplayed() })
            }

            HOTELS_ID -> {
                Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.book_a_hotel))
                Espresso.onView(ViewMatchers.withId(R.id.fab_book_hotels)).check({ view, viewNotFoundException -> ViewMatchers.isDisplayed() })
            }

            SETTINGS_ID -> {
                Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.settings))
                Espresso.onView(ViewMatchers.withId(R.id.settings_tv)).check({ view, viewNotFoundException -> ViewMatchers.isDisplayed() })
            }

            ABOUT_ID -> {
                Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.about))
                Espresso.onView(ViewMatchers.withId(R.id.about_tv)).check({ view, viewNotFoundException -> ViewMatchers.isDisplayed() })
            }
        }
    }

    private fun actionOpenDrawer(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(DrawerLayout::class.java)
            }

            override fun getDescription(): String {
                return "Open drawer"
            }

            override fun perform(uiController: UiController, view: View) {
                (view as DrawerLayout).openDrawer(GravityCompat.START)
            }
        }
    }

}