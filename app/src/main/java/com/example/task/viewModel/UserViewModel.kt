package com.example.task.viewModel

import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.task.data.model.User
import com.example.task.data.remote.ApiState
import com.example.task.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userState = MutableLiveData<ApiState<List<User>>>()

    var userState: LiveData<ApiState<List<User>>> = _userState

    fun getUsers() {

        viewModelScope.launch {
            _userState.value = ApiState.Loading
            try {
               val users = repository.fetchUsers()

                _userState.value = ApiState.Success(users)
            } catch (e: Exception) {
                _userState.value = ApiState.Error(e.message?: "Unknown error")
                e.printStackTrace()
            }
        }

    }

    fun deleteUsers(user: User) {
        viewModelScope.launch {
            try {
                repository.deleteUser(user)

                val updatedList = repository.fetchUsers()
                _userState.value = ApiState.Success(updatedList)
            } catch (e: Exception) {
                _userState.value = ApiState.Error(e.message ?: "Failed to delete user")
            }
        }
    }

}


class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}