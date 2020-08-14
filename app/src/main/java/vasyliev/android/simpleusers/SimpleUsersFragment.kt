package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_user.*
import java.util.*

private const val ARG_USER_ID = "user_id"

class SimpleUsersFragment : Fragment() {
    companion object {
        fun newInstance(userId: UUID): SimpleUsersFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USER_ID, userId)
            }
            return SimpleUsersFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var user: SimpleUsersData
    private val userDetailViewModel: SimpleUsersDetailViewModel by lazy {
        ViewModelProvider(this).get(SimpleUsersDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        user = SimpleUsersData()
        val userId: UUID = arguments?.getSerializable(ARG_USER_ID) as UUID
        userDetailViewModel.loadUser(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailViewModel.userLiveData.observe(
            viewLifecycleOwner,
            Observer { user ->
                user?.let {
                    this.user = user
                    updateUI()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        editTextFirstName.doOnTextChanged { userFragmentFirstNameText, _, _, _ ->
            user.firstName = userFragmentFirstNameText.toString()
        }
        editTextLastName.doOnTextChanged { userFragmentLastNameText, _, _, _ ->
            user.lastName = userFragmentLastNameText.toString()
        }
    }

    override fun onStop() {
        super.onStop()
        userDetailViewModel.saveUser(user)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_user, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_user -> {
                userDetailViewModel.deleteUser(user)
                activity!!.onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI() {
        editTextFirstName.setText(user.firstName)
        editTextLastName.setText(user.lastName)
    }
}