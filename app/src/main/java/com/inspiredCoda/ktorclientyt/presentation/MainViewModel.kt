package com.inspiredCoda.ktorclientyt.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredCoda.ktorclientyt.data.NetworkEvents
import com.inspiredCoda.ktorclientyt.data.Repository
import com.inspiredCoda.ktorclientyt.domain.data.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _userState = Channel<UIState>(Channel.BUFFERED)
    val userState = _userState.receiveAsFlow()

    sealed interface UIState {
        object Loading : UIState
        data class Success(val users: List<User>) : UIState
        data class Failure(val message: String) : UIState
    }

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            _userState.send(UIState.Loading)
            when (val response = repository.getUser()) {
                is NetworkEvents.Success<*> -> {
                    val data = response.data as List<User>
                    _userState.send(UIState.Success(data))
                }
                is NetworkEvents.Failure -> {
                    _userState.send(UIState.Failure(response.message))
                }
            }
        }
    }

}