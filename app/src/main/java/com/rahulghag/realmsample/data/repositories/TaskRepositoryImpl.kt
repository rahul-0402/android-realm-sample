package com.rahulghag.realmsample.data.repositories

import com.rahulghag.realmsample.data.local.TaskEntity
import com.rahulghag.realmsample.data.local.TaskMapper
import com.rahulghag.realmsample.domain.models.Task
import com.rahulghag.realmsample.domain.repositories.TaskRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class TaskRepositoryImpl(
    private val realm: Realm,
    private val taskMapper: TaskMapper
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return realm.query<TaskEntity>().asFlow()
            .map { taskEntities -> taskEntities.list.map { taskMapper.mapToDomainModel(it) } }
    }

    override suspend fun addTask(title: String) {
        realm.write {
            copyToRealm(TaskEntity().apply { this.title = title })
        }
    }

    override suspend fun updateTask(id: String, isCompleted: Boolean) {
        realm.write {
            query<TaskEntity>(query = "_id == $0", ObjectId(hexString = id)).first().find()?.also {
                it.isCompleted = !isCompleted
            }
        }
    }

    override suspend fun deleteCompletedTasks() {
        realm.write {
            val completedTasks: RealmResults<TaskEntity> =
                query<TaskEntity>("isCompleted == true").find()
            delete(completedTasks)
        }
    }
}