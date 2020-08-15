package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_whats_new.*

class WhatsNewFragment : Fragment() {
    private val whatsNewViewModel: WhatsNewViewModel by lazy {
        ViewModelProvider(this).get(WhatsNewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_whats_new, container, false)
        whatsNewViewModel.text.observe(viewLifecycleOwner, Observer {
            textViewWhatsNewText.text = it
        })
        return view
    }
}