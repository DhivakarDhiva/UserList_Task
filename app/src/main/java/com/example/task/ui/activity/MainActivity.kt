package com.example.task.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.local.UserDatabase
import com.example.task.data.model.User
import com.example.task.data.remote.ApiState
import com.example.task.data.repository.UserRepository
import com.example.task.ui.adapter.UserAdapter
import com.example.task.viewModel.UserViewModel
import com.example.task.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            UserRepository(
                UserDatabase.getDatabase(this).userDao()
            )
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

         recyclerView = findViewById(R.id.recyclerview)

        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<User>()

        userViewModel.getUsers()

        userViewModel.userState.observe(this) { state ->

            when (state) {
                is ApiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }

                is ApiState.Success -> {
                    progressBar.visibility = View.GONE
                    data.clear()
                    data.addAll(state.data)

                }

                is ApiState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()

                }
            }

            adapter = UserAdapter(this, data) { user ->
                userViewModel.deleteUsers(user)
            }
            recyclerView.adapter = adapter
        }
    }
}