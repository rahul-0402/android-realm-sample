## Screenshots

<img src="screenshots/img_1.png" height="600"/>

## Realm Initialization

```
@Provides
@Singleton
fun provideRealm(): Realm {
    val configuration = RealmConfiguration.Builder(
        schema = setOf(TaskEntity::class)
    ).compactOnLaunch().build()
    return Realm.open(configuration = configuration)
}
```

## Define Object Model

```
class TaskEntity : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var title: String = ""
    var isCompleted: Boolean = false
    var timestamp: RealmInstant = RealmInstant.now()
}
```

## CRUD

```
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
```
