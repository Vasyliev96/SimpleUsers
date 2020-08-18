package vasyliev.android.simpleusers.ui.edituserpage.uifragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_user.*
import vasyliev.android.simpleusers.R
import vasyliev.android.simpleusers.ui.edituserpage.viewmodel.SimpleUsersDetailViewModel
import vasyliev.android.simpleusers.db.SimpleUsersData
import java.util.*

private const val ARG_USER_ID = "user_id"

class SimpleUsersFragment : Fragment() {

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
                    userDetailViewModel.setUserNotNew()
                    updateUI()
                }
            })
    }

    override fun onStart() {
        super.onStart()

        fabFragmentUser.setOnClickListener {
            val imm: InputMethodManager =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            if (user.firstName != "" && user.lastName != "") {
                if (!userDetailViewModel.isUserNew()) {
                    userDetailViewModel.saveUser(user)
                    Toast.makeText(
                        context,
                        resources.getString(R.string.toast_text_user_successfully_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    userDetailViewModel.addUser(user)
                    Toast.makeText(
                        context,
                        resources.getString(R.string.toast_text_user_successfully_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.toast_text_user_not_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        editTextFirstName.doOnTextChanged { userFragmentFirstNameText, _, _, _ ->
            user.firstName = userFragmentFirstNameText.toString()
        }
        editTextLastName.doOnTextChanged { userFragmentLastNameText, _, _, _ ->
            user.lastName = userFragmentLastNameText.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_user, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_user -> {
                val imm: InputMethodManager =
                    requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                userDetailViewModel.deleteUser(user)
                requireActivity().onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI() {
        editTextFirstName.setText(user.firstName)
        editTextLastName.setText(user.lastName)
    }

    companion object {
        fun newInstance(userId: UUID): SimpleUsersFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USER_ID, userId)
            }
            return SimpleUsersFragment()
                .apply {
                    arguments = args
                }
        }
    }
}