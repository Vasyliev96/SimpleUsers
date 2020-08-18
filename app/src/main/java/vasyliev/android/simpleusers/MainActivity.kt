package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import vasyliev.android.simpleusers.ui.aboutus.uifragments.AboutUsFragment
import vasyliev.android.simpleusers.ui.edituserpage.uifragments.SimpleUsersFragment
import vasyliev.android.simpleusers.ui.homepage.uifragments.SimpleUsersListFragment
import vasyliev.android.simpleusers.ui.whatsnew.uifragments.WhatsNewFragment
import java.util.*


class MainActivity : AppCompatActivity(), SimpleUsersListFragment.CallbackOnUserSelected,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentRouter.initialize(this)
        FragmentRouter.setInitialFragment(R.id.nav_host_fragment)

        setDrawerLayout(setToolbar())
        setNavViewListener()
        setAppBarConfiguration()
    }

    override fun onDestroy() {
        super.onDestroy()
        FragmentRouter.detach()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

    override fun onUserSelected(userId: UUID) {
        FragmentRouter.addFragment(R.id.nav_host_fragment, SimpleUsersFragment.newInstance(userId))
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                FragmentRouter.apply {
                    popBackStack()
                    replaceFragment(R.id.nav_host_fragment, SimpleUsersListFragment.newInstance())
                }
            }
            R.id.nav_about_us -> {
                FragmentRouter.apply {
                    popBackStack()
                    addFragment(R.id.nav_host_fragment, AboutUsFragment())
                }
            }
            R.id.nav_slideshow -> {
                FragmentRouter.apply {
                    popBackStack()
                    addFragment(R.id.nav_host_fragment, WhatsNewFragment())
                }
            }
        }
        return true
    }

    private fun setNavViewListener() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    private fun setDrawerLayout(toolbar: Toolbar) {
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
    }

    private fun setAppBarConfiguration() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_slideshow
            ), drawerLayout
        )
    }

    private fun setToolbar(): Toolbar {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        return toolbar
    }
}
