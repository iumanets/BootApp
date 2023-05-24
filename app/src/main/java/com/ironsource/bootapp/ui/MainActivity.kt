package com.ironsource.bootapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.ironsource.bootapp.R
import com.ironsource.bootapp.ui.state.ScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO REQUEST NOTIFICATION PERMISSION

        setContentView(R.layout.activity_main)
        val contentView  =  findViewById<TextView>(R.id.content_view)

        viewModel.uiState.observe(this) {
            when (it) {
                is ScreenState.Loading -> contentView.text = "Loading"
                is ScreenState.Loaded -> contentView.text = it.text
                ScreenState.Error -> contentView.text = "Error"
            }
        }
    }

}