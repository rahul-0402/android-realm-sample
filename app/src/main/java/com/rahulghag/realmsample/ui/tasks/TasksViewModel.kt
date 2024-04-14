package com.rahulghag.realmsample.ui.tasks

import androidx.lifecycle.viewModelScope
import com.rahulghag.realmsample.R
import com.rahulghag.realmsample.domain.models.Task
import com.rahulghag.realmsample.domain.repositories.TaskRepository
import com.rahulghag.realmsample.ui.theme.utils.BaseViewModel
import com.rahulghag.realmsample.ui.theme.utils.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel<TasksContract.State, TasksContract.Event, TasksContract.Effect>() {

    init {
        setEvent(TasksContract.Event.GetTasks)
    }

    override fun createInitialState() = TasksContract.State()

    override fun handleEvent(event: TasksContract.Event) {
        when (event) {
            TasksContract.Event.GetTasks -> {
                getTasks()
            }

            is TasksContract.Event.TitleInputChanged -> {
                setState { copy(title = event.title) }
            }

            TasksContract.Event.AddTask -> {
                addTask(title = currentState.title)
                setState { copy(title = "") }
            }

            is TasksContract.Event.UpdateTask -> {
                updateTask(event.task)
            }

            TasksContract.Event.DeleteCompletedTasks -> {
                deleteCompletedTasks()
            }
        }
    }

    private fun getTasks() = viewModelScope.launch {
        taskRepository.getTasks().collect {
            setState { copy(tasks = it) }
        }
    }

    private fun addTask(title: String) = viewModelScope.launch(Dispatchers.IO) {
        if (title.isEmpty()) {
            setEffect { TasksContract.Effect.ShowMessage(UiMessage.StringResource(R.string.error_empty_title)) }
            return@launch
        }
        taskRepository.addTask(title = title)
    }

    private fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.updateTask(id = task.id, isCompleted = task.isCompleted)
    }

    private fun deleteCompletedTasks() = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.deleteCompletedTasks()
    }
}