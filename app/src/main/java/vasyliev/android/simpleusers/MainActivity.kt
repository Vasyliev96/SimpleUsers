package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), SimpleUsersListFragment.CallbackOnUserSelected,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment == null) {
            val fragment = SimpleUsersListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment, fragment)
                .commit()
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_slideshow
            ), drawerLayout
        )
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

    override fun onUserSelected(userId: UUID) {
        val fragment = SimpleUsersFragment.newInstance(userId)
        replaceFragment(
            fragment,
            ifPopBackStack = false,
            ifAddToBackStack = true
        )
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                replaceFragment(
                    SimpleUsersListFragment.newInstance(),
                    ifPopBackStack = true,
                    ifAddToBackStack = false
                )
            }
            R.id.nav_about_us -> {
                replaceFragment(
                    AboutUsFragment(),
                    ifPopBackStack = true,
                    ifAddToBackStack = true
                )
            }
            R.id.nav_slideshow -> {
                replaceFragment(
                    WhatsNewFragment(),
                    ifPopBackStack = true,
                    ifAddToBackStack = true
                )
            }
        }
        return true
    }

    private fun replaceFragment(
        fragment: Fragment,
        ifPopBackStack: Boolean,
        ifAddToBackStack: Boolean
    ) {//when
        if (ifPopBackStack) {
            supportFragmentManager.popBackStack()
        }
        if (ifAddToBackStack) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }
    }
}
