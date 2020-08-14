package vasyliev.android.simpleusers

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_user.view.*
import java.util.*

class SimpleUsersListFragment : Fragment() {
    companion object {
        fun newInstance(): SimpleUsersListFragment {
            return SimpleUsersListFragment()
        }
    }

    interface Callbacks {
        fun onUserSelected(userId: UUID)
    }

    private var callbacks: Callbacks? = null
    private var adapter: SimpleUsersAdapter? = SimpleUsersAdapter(emptyList())
    private lateinit var userRecyclerView: RecyclerView
    private val userViewModel: SimpleUsersViewModel by lazy {
        ViewModelProvider(this).get(SimpleUsersViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        userRecyclerView = view.findViewById(R.id.simpleUserRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.adapter = adapter
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

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_user, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_user -> {
                val user = SimpleUsersData()
                userViewModel.addUser(user)
                callbacks?.onUserSelected(user.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(users:List<SimpleUsersData>) {
        adapter = SimpleUsersAdapter(users)
        userRecyclerView.adapter = adapter
    }

    private inner class SimpleUsersHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var user: SimpleUsersData
        val userFirstNameTV: TextView = itemView.textViewFirstName
        val userLastNameTV: TextView = itemView.textViewLastName


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(user: SimpleUsersData) {
            this.user = user
            userFirstNameTV.text = this.user.firstName
            userLastNameTV.text = this.user.lastName
        }

        override fun onClick(v: View?) {
            callbacks?.onUserSelected(user.id)
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
}