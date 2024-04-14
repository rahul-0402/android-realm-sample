package com.rahulghag.realmsample.ui.tasks

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulghag.realmsample.R
import com.rahulghag.realmsample.domain.models.Task
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is TasksContract.Effect.ShowMessage -> {
                    Toast.makeText(context, effect.uiMessage?.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Column {
            TextField(
                value = uiState.title,
                onValueChange = {
                    viewModel.setEvent(TasksContract.Event.TitleInputChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Title")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Button(
                    onClick = {
                        viewModel.setEvent(TasksContract.Event.DeleteCompletedTasks)
                    },
                ) {
                    Text(text = stringResource(R.string.delete_completed))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        viewModel.setEvent(TasksContract.Event.AddTask)
                    },
                ) {
                    Text(text = stringResource(R.string.add))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(items = uiState.tasks, key = { it.id }) {
                TaskItem(
                    task = it,
                    onUpdateTask = { viewModel.setEvent(TasksContract.Event.UpdateTask(it)) })
            }
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onUpdateTask: (Task) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val title = buildAnnotatedString {
            if (task.isCompleted) {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                ) {
                    append(task.title)
                }
            } else {
                append(task.title)
            }
        }

        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onUpdateTask(task)
            }
        )
    }
}

@Preview
@Composable
private fun TaskPreview() {
    Surface {
        TaskItem(
            task = Task(
                id = "",
                title = "Go for a walk",
                timestamp = "2 hours ago",
                isCompleted = true
            ),
            onUpdateTask = {}
        )
    }
}