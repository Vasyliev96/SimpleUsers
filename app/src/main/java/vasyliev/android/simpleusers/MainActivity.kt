package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), SimpleUsersListFragment.Callbacks,
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

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Alert")
            alertDialog.setMessage("Alert message to be shown")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.popBackStack()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, SimpleUsersListFragment.newInstance())
                    .commit()
            }
            R.id.nav_about_us -> {
                supportFragmentManager.popBackStack()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, AboutUsFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_slideshow -> {
                supportFragmentManager.popBackStack()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, WhatsNewFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        return true
    }
}
