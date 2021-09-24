# GooglePlay Licensing Library - LicenseChecker
The play-licensing library is a modified project from https://github.com/google/play-licensing

[For more details, visit our confluence page.](https://navifypoc.atlassian.net/wiki/spaces/DHS/pages/1328152806/Google+Play+Licensing)

The play-license library is enhanced to include server side validation.  
For offline-mode, it will default to client-side check.

Note:
If you would like to use online-mode only, you can check for network connectivity when your app is 
launched.  You may use the NetworkUtils from the utils library to verify the network connectivity.

## How To Add Library
The play-license library is part of the SSG Security library.  Therefore, to add play-licensing check
to your app, please include the security library.

[For more details, visit the security library.](https://bitbucket.org/rochedis/common-sdk-android/src/dev/RocheCommonSDK/security/)

### Add following properties in gradle.properties file
```
// request access to JFrog from your manager / tech lead
artifactory.user=********** // replace with your JFrog's user name
artifactory.password=********** // replace with your JFrog's password
```
### Integrate Library
* From JFrog, Add maven to project's build.gradle inside allprojects sections:
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
* Then add the dependency in the app's build.gradle
```
implementation "RocheCommonComponent:security:1.0"
```
### Or you can download the library manually from JFrog, add the AAR file to your libs folder.
* [Add the security library project by following the instructions here.](https://developer.android.com/studio/projects/android-library#CreateLibrary)
* Then add the dependency in the app's build.gradle
```
implementation project(path: ':security')
```
### Currently, the play-licensing requires the following dependencies:
```
// Retrofit for License Validation
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:3.11.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

## How To Use
### To enable or disable the license check for a specific build variant or flavor 
Add a buildConfigField in your app's build.gradle file:
```
    buildTypes {
        release {
            buildConfigField "boolean", "VALIDATE_LICENSE", "true"
        }
        debug {
            buildConfigField "boolean", "VALIDATE_LICENSE", "false"
        }
    }
```
### In your Splash Screen or Main Activity you can extend the SecurityCheckerActivity.
Note that you should check for the license when the app is restarted / foregrounded again.
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
                    // TODO
                }
                SecurityCheckerViewState.InvalidLicense -> {
                    // TODO
                }
                SecurityCheckerViewState.Retry -> {
                    // TODO
                }
                SecurityCheckerViewState.ValidLicense, SecurityCheckerViewState.IgnoreSecurityCheck -> {
                    // TODO
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        securityViewModel.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        securityViewModel.validate(
            licensingKey, // string pulic license key from your GooglePlay account.
            baseUrl, // string URL for your app's BE license API
            shouldValidateLicense, // boolean
            isOfflineMode // boolean
        )
    }
```