## Screenshots

<img src="screenshots/img_1.png" height="600"/>

##

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
