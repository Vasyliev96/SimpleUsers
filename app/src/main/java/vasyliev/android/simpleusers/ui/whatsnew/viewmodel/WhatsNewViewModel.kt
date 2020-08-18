package vasyliev.android.simpleusers.ui.whatsnew.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WhatsNewViewModel : ViewModel() {

    private val whatsNewText = MutableLiveData<String>().apply {
        value = "Some update info"
    }
    val text: LiveData<String> = whatsNewText
}