package com.rahulghag.realmsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahulghag.realmsample.ui.tasks.TasksScreen
import com.rahulghag.realmsample.ui.tasks.TasksViewModel
import com.rahulghag.realmsample.ui.theme.RealmSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealmSampleTheme {
                val viewModel: TasksViewModel = hiltViewModel()
                TasksScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}