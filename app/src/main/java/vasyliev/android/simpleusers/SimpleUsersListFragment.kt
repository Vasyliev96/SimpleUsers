package vasyliev.android.simpleusers

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.list_item_user.view.*
import java.util.*

class SimpleUsersListFragment : Fragment() {

    private var callbackOnUserSelected: CallbackOnUserSelected? = null
    private var simpleUsersAdapter: SimpleUsersAdapter? = SimpleUsersAdapter(emptyList())
    private lateinit var userRecyclerView: RecyclerView
    private val userViewModel: SimpleUsersViewModel by lazy {
        ViewModelProvider(this).get(SimpleUsersViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbackOnUserSelected = context as CallbackOnUserSelected?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        userRecyclerView = view.findViewById(R.id.simpleUserRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.adapter = simpleUsersAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.userListLiveData.observe(
            viewLifecycleOwner,
            Observer { users ->
                users?.let {
                    updateUI(users)
                }
            })
    }

    override fun onStart() {
        super.onStart()

        fabFragmentUserList.setOnClickListener {
            val user = SimpleUsersData()
            callbackOnUserSelected?.onUserSelected(user.id)
        }


    }

    override fun onDetach() {
        super.onDetach()
        callbackOnUserSelected = null
    }

    private fun updateUI(users: List<SimpleUsersData>) {
        simpleUsersAdapter = SimpleUsersAdapter(users)
        userRecyclerView.adapter = simpleUsersAdapter
    }

    private inner class SimpleUsersHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var user: SimpleUsersData
        val userFirstNameTV: TextView = itemView.textViewFirstName
        val userLastNameTV: TextView = itemView.textViewLastName
        val editUserButton: Button = itemView.buttonEditUser


        init {
            itemView.setOnClickListener(this)
            editUserButton.setOnClickListener { callbackOnUserSelected?.onUserSelected(user.id) }
        }

        fun bind(user: SimpleUsersData) {
            this.user = user
            userFirstNameTV.text = this.user.firstName
            userLastNameTV.text = this.user.lastName
        }

        override fun onClick(view: View) {
            val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
            alertDialog.setTitle("You selected ${layoutPosition + 1} user")
            alertDialog.setMessage("${user.firstName} ${user.lastName}")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private inner class SimpleUsersAdapter(var users: List<SimpleUsersData>) :
        RecyclerView.Adapter<SimpleUsersHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : SimpleUsersHolder {
            val view = layoutInflater.inflate(R.layout.list_item_user, parent, false)
            return SimpleUsersHolder(view)
        }

        override fun getItemCount() = users.size
        override fun onBindViewHolder(holder: SimpleUsersHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }

    interface CallbackOnUserSelected {
        fun onUserSelected(userId: UUID)
    }

    companion object {
        fun newInstance(): SimpleUsersListFragment {
            return SimpleUsersListFragment()
        }
    }
}