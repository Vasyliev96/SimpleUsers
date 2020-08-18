package vasyliev.android.simpleusers.ui.aboutus.uifragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_about_us.*
import vasyliev.android.simpleusers.ui.aboutus.viewmodel.AboutUsViewModel
import vasyliev.android.simpleusers.R

class AboutUsFragment : Fragment() {

    private val aboutUsViewModel: AboutUsViewModel by lazy {
        ViewModelProvider(this).get(AboutUsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutUsViewModel.text.observe(viewLifecycleOwner, Observer {
            textViewAboutUsText.text = it
        })
    }
}