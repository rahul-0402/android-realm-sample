package com.rahulghag.realmsample.data.local

import com.rahulghag.realmsample.data.utils.DomainMapper
import com.rahulghag.realmsample.domain.models.Task

class TaskMapper : DomainMapper<TaskEntity, Task> {
    override fun mapToDomainModel(data: TaskEntity): Task {
        return Task(
            id = data._id.toHexString(),
            title = data.title,
            isCompleted = data.isCompleted,
            timestamp = data.timestamp.toString()
        )
    }
}