package ru.mrfix1033.saveinstancestate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mrfix1033.listview.data.User

class UserViewModel : ViewModel() {
    val currentList = MutableLiveData(mutableListOf<User>())
}