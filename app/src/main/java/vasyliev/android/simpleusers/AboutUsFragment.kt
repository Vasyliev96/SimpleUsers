package vasyliev.android.simpleusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_about_us.*

class AboutUsFragment : Fragment() {

    private val aboutUsViewModel: AboutUsViewModel by lazy {
        ViewModelProvider(this).get(AboutUsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        aboutUsViewModel.text.observe(viewLifecycleOwner, Observer {
            textViewAboutUsText.text = it
        })
        return view
    }
}