package com.kchienja.learning

import androidx.lifecycle.ViewModel

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kchienja.learning.data.mails
import com.kchienja.learning.model.MailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel()  {
    // profile modal state
    private var _isDialogOpen = MutableLiveData(false)
    var dialogState : LiveData<Boolean> = _isDialogOpen
    //    search widget state
    private var _inSearchMode = MutableLiveData(false)
    var inSearchMode : LiveData<Boolean> = _inSearchMode
    // mail list state
    private val _cards = MutableStateFlow(listOf<MailModel>())
    val cards: StateFlow<List<MailModel>> get() = _cards
    // flow state of expanded card
    //    private  state
    private var currentMailPosition by mutableStateOf(-1)

    var mailItems = mutableStateListOf<MailModel>()
        private set
    //    state
    val currentMailItem: MailModel?
        get() = mailItems.getOrNull(currentMailPosition)

    // event: onMailItemSelected
    fun onMailItemSelected(item: MailModel){
        currentMailPosition = mailItems.indexOf(item)
    }
    // event: onMailDone
    fun onMailDone(){
        currentMailPosition = -1
    }
    // event: onEditItemChange
    fun onMailItemChange(item: MailModel){
        val currentItem = requireNotNull(currentMailItem)

        require(currentItem.id ==item.id){
            "You can only change an item with the same id as currentEditItem"
        }
        mailItems[currentMailPosition] = item
    }











    fun openDialog(status: Boolean) {
        _isDialogOpen.value = status
    }
    fun openSearch(status: Boolean){
        _inSearchMode.value = status
    }
    init {
        getFakeData()
        mailItems = mails
    }

    private fun getFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            val testList = arrayListOf<MailModel>()
           testList += mailList
            _cards.emit(testList)
        }
    }

//    fun onCardArrowClicked(cardId: Int) {
//        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
////            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
//        }
//    }



}

