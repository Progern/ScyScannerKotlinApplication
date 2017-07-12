package com.progern.scyscannerapplication.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.progern.scyscannerapplication.R
import com.progern.scyscannerapplication.Utils.RC_SIGN_IN
import com.stephentuso.welcome.WelcomeHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import android.widget.TextView
import com.progern.scyscannerapplication.Activities.Main.BookHotelsActivity
import com.progern.scyscannerapplication.Activities.Main.FindFlightsActivity
import com.progern.scyscannerapplication.Activities.Main.RentCarActivity
import com.progern.scyscannerapplication.Activities.Main.SettingsActivity
import com.progern.scyscannerapplication.Activities.Other.AboutActivity
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mWelcomeHelper: WelcomeHelper
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        title = ""
        mWelcomeHelper = WelcomeHelper(this, WelcomeSplashActivity::class.java)
        mWelcomeHelper.show(savedInstanceState)

        mFirebaseAuth = FirebaseAuth.getInstance()
        initAuthListener()
        initUserCredentialsInNavigationDrawer()

    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu -> {
                startActivity(intentFor<SettingsActivity>())
                return true
            }
            R.id.sign_out_menu -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.find_flight -> {
                startActivity(intentFor<FindFlightsActivity>())
            }

            R.id.book_a_hotel -> {
                startActivity(intentFor<BookHotelsActivity>())
            }

            R.id.rent_car -> {
                startActivity(intentFor<RentCarActivity>())
            }

            R.id.settings -> {
                startActivity(intentFor<SettingsActivity>())
            }

            R.id.about -> {
                startActivity(intentFor<AboutActivity>())
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Signed up!", Toast.LENGTH_SHORT).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Sign up canceled.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        mWelcomeHelper.onSaveInstanceState(outState)
    }

    private fun initAuthListener() {
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setLogo(R.drawable.scyscanner_logo_transp)
                                .setTheme(R.style.AppTheme)
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(
                                        Arrays.asList<AuthUI.IdpConfig>(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN)
            }
        }
    }

    private fun initUserCredentialsInNavigationDrawer() {
        val user = FirebaseAuth.getInstance().currentUser
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<View>(R.id.nav_user_name) as TextView
        val email = header.findViewById<View>(R.id.nav_user_email) as TextView
        val avatar = header.findViewById<View>(R.id.nav_user_avatar) as ImageView

        name.text = user?.displayName ?: "Anonymous"
        email.text = user?.email ?: ""
        avatar.setImageURI(user?.photoUrl)

    }
}
