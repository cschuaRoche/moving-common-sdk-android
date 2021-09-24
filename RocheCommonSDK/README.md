# Roche common SDK

This repository contains common SDKs which are developed and maintained by the Software Solution Group (SSG)

## Description

The solutions are:
1. biometrics library
2. play-license library
3. recall library
4. rocheCommon library
5. security library
6. staticContent library
7. utils library
For more information, please check the individual library projects.

### MVVM Structure

We encourage the use of Clean Architecture and MVVM

#### How to use Clean Architecture

```
class ExampleViewModel(
    private val getExampleData: GetExampleData
) : ViewModel(), LifecycleObserver,
    ViewStateHolder<ExampleData> by ViewStateHolderImpl(),
    NavStateHolder by NavStateHolderImpl() {

    init {
        fetchViewState()
    }

    private fun fetchViewState() {
        viewModelScope.launch {
            getExampleData(None).handle(
                onError = {
                },
                onSuccess = { response ->
                    Log.d("ExampleViewModel", response.data)
                }
            )
        }
    }
}

class GetExampleData(private val repository: ExampleRepository) : Interactor<None, ExampleData>() {
    override suspend fun run(params: None): Either<Throwable, ExampleData> = coroutineScope {
        repository.getLongRunningData()
    }
}

class GetAnotherExampleData(private val repository: ExampleRepository) : Interactor<None, ExampleData>() {
    override suspend fun run(params: None): Either<Throwable, ExampleData> {
        val result = repository.getSomeData()
        if (result.data.isNullOrBlank()) {
            return Either.Left(Throwable("Something went wrong!"))
        }
        return Either.Right(result)
    }
}

interface ExampleRepository {
    suspend fun getLongRunningData(): Either<Throwable, ExampleData>
    fun getSomeData(): ExampleData
}

data class ExampleData(
    val data: String
)
```
