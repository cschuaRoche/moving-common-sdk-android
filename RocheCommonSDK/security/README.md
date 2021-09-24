# Security

## How To Add Library
### Add maven to project's build.gradle inside allprojects sections:
```
  allprojects {
      repositories {
          google()
          jcenter()

          maven {
              url "https://dhs.jfrog.io/dhs/mobile-android/"
              // The Artifactory (preferably virtual) repository to resolve from
              credentials {
                  username = getProperty('artifactory.user')
                  password = getProperty('artifactory.password')
              }
          }
      }
  }
```
### Add Dependency in app build.gradle
```
implementation "RocheCommonComponent:security:1.0"
```
###  Add following properties in gradle.properties(Project Properties) file
```
artifactory.user=**********
artifactory.password=**********
```

## How to check if device is Rooted
```
RootDetectUtil.isDeviceRooted()
```
## How to use the SecurityChecker
The SecurityChecker consist of an abstract SecurityCheckerActivity and a SecurityCheckerViewModel.
It will check if the device is rooted and also verify if the app is licensed properly (meaning it
was downloaded from GooglePlay).  These two security checks are combined together to make sure that
hackers can't modify the app or the configurations when checking for a valid license.
### In your Splash Screen or Main Activity you can extend the SecurityCheckerActivity.
```
class SplashActivity : SecurityCheckerActivity() {
    
    override fun provideBaseUrl(): String {
        val country = FloodlightApplication.getApplicationCountry(this)
        val environment = country?.environment ?: getEnvironment(SupportedCountry.US)
        return environment.url + "/"

    }

    override fun shouldValidateLicense() = BuildConfig.VALIDATE_LICENSE // boolean to enable/disable

    override fun provideLicensingKey() = YOUR_LICENSE_KEY

    override fun onInvalidLicense() {
        // show a blocking error popup or exit the app to prevent user from using the app
    }
}
```
### You may also integrate the SecurityCheckerViewModel instead.
```
class SplashActivity : YourBaseActivity() {
    private val securityViewModel: SecurityCheckerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        securityViewModel.viewState.observe(this) {
            when (it) {
                SecurityCheckerViewState.DeviceIsRooted -> {
                    // take action if device is rooted
                }
                SecurityCheckerViewState.InvalidLicense -> {
                    // take action if app has an invalid license
                }
                SecurityCheckerViewState.Retry -> {
                    // take action if state is Retry, which usually means a network failure.
                    // you may add retry logic and call securityViewModel.validate again.
                }
                SecurityCheckerViewState.ValidLicense, SecurityCheckerViewState.IgnoreLicenseCheck -> {
                    // take action when license is valid
                    // if shouldValidateLicense is false, the IgnoreLicenseCheck will trigger instead
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // you should validate every time the UI is resumed
        securityViewModel.validate(
            licensingKey, // string pulic license key from your GooglePlay account.
            baseUrl, // string URL for your app's BE license API
            shouldValidateLicense, // boolean
            isOfflineMode // boolean
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        securityViewModel.onDestroy() // make sure to call destroy to avoid memory leaks
    }
```