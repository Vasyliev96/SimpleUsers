package vasyliev.android.simpleusers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vasyliev.android.simpleusers.ui.homepage.uifragments.SimpleUsersListFragment

object FragmentRouter {
    private var activity: AppCompatActivity? = null

    fun initialize(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun detach() {
        activity = null
    }

    fun setInitialFragment(fragmentId: Int) {
        val currentFragment = activity?.supportFragmentManager?.findFragmentById(fragmentId)
        if (currentFragment == null) {
            val fragment = SimpleUsersListFragment.newInstance()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(fragmentId, fragment)
                ?.commit()
        }
    }

    fun popBackStack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    fun addFragment(fragmentId: Int, fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(fragmentId, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    fun replaceFragment(fragmentId: Int, fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(fragmentId, fragment)
            ?.commit()
    }
}