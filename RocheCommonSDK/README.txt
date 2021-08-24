# Roche common SDK

This is the common SDK which provides important utility which you can use in your projects.

## Description

This Project provide below utility functionality those made project scalable, secure.
1. Google play licensing side loading
2. Bio-metric APIs.
3. Device is rooted or not.
4. Clean Architecture with MVVM predefine structure.

### Google play licensing
#### How use the LicenseChecker
 - Create the object of the LicenseChecker class. Which require Context, Policy policy and PublicKey as parameter.
 - Call the method checkAccess on the object of LicenseChecker with callback and the base URL and offline mode boolean flag.
 - Callback method will be called if licence is allow, dontAllow or applicationError.
 - Take the appropriate decision at the client side based on the callback result.

### Bio-metric APIs
#### How to use BioMetric
 - Create the object of RocheBiometricsManager which require the context and the allowedAuthenticator
 - RocheBiometricsManager has multiple methods to check if Biometric status of the device
 - Authenticate with the showAuthDialog method with activity or fragment and OnAuthenticationCallback.
 - Need to override the onAuthComplete of the call back in that we receives the statusCodes 
  * BiometricStatusConstants.SUCCESS,
  * BiometricStatusConstants.ERROR_USER_CANCELED,
  * BiometricStatusConstants.ERROR_NO_HARDWARE,
  * BiometricStatusConstants.ERROR_NO_BIOMETRICS,
  * BiometricStatusConstants.ERROR_LOCKOUT,
  * BiometricStatusConstants.ERROR_UNKNOWN,
  * BiometricStatusConstants.FAILED_ATTEMPT
  - With isFingerprintSupported() check that fingerprint is available or can be setup in the device settings
  - With isFaceSupported() check that face unlock is available or can be setup in the device settings
  - With isIrisSupported() check that iris is available or can be setup in the device settings

### MVVM Structure
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
