package com.rahulghag.realmsample.ui.tasks

import com.rahulghag.realmsample.domain.models.Task
import com.rahulghag.realmsample.ui.theme.utils.UiEffect
import com.rahulghag.realmsample.ui.theme.utils.UiEvent
import com.rahulghag.realmsample.ui.theme.utils.UiMessage
import com.rahulghag.realmsample.ui.theme.utils.UiState

class TasksContract {
    data class State(
        val tasks: List<Task> = emptyList(),
        val title: String = "",
        val isLoading: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data object GetTasks : Event()
        data class TitleInputChanged(val title: String) : Event()
        data object AddTask : Event()
        data class UpdateTask(val task: Task) : Event()
        data object DeleteCompletedTasks : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowMessage(val uiMessage: UiMessage?) : Effect()
    }
}