package vasyliev.android.simpleusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutUsViewModel : ViewModel() {

    private val aboutUsText = MutableLiveData<String>().apply {
        value = "SMT about us"
    }
    val text: LiveData<String> = aboutUsText
}