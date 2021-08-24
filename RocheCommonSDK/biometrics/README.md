Biometric
========
A library used for Authentication

How To Use
----------

1. Add dependency
    - Add URL in root build.gradle allprojects sections
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
    - Add Dependency in app build.gradle
        ```
        implementation "RocheCommonComponent:biometrics:1.1"
        ```
    - Add following properties in gradle.properties(Project Proerties) file
        ```
        artifactory.user=**********
        artifactory.password=**********
        ```
2. Example code
    - Implement Biometric callback
        ```
        com.roche.roche.dis.biometrics.callback.OnAuthenticationCallback
        ```
    - Create object of RocheBiometricsManager
        ```
        val biometricsManager = RocheBiometricsManager(requireContext(), Authenticator.STRONG)
        ```
    - Following method can be used to check if Biometric Hardware is suppoeted by device. This method returns true if either Fingerprint or Face or Iris hardware is available, otherwise false
        ```
        biometricsManager.isBiometricSupported()
        ```
    - You can also check individual hardware support
        - Check if Fingerprint hardware is available
            ```
            biometricsManager.isFingerprintSupported()
            ``` 
        - Check if Face hardware is available
            ```
            biometricsManager.isFaceSupported()
            ``` 
        - Check if Iris hardware is available
            ```
            biometricsManager.isFaceSupported()
            ``` 
    - Following method check if Biometric is set on device
        ```
        biometricsManager.isBiometricsEnrolled()
        ```
        if Biometric is not set on device then following method can be used to redirect device to set Biometric
        ```
        biometricsManager.enrollBiometric()
        ```
    - Following method show Biometric dialog on screen
        ```
        biometricsManager.showAuthDialog(this,this)
        ```
        This method call ```OnAuthenticationCallback``` callback, which has status of authetication. Please check documation for status detail
3. Full Example
    ```
    val biometricsManager = RocheBiometricsManager(requireContext(), Authenticator.STRONG)
    biometricsManager.showAuthDialog(this,this)

    override fun onAuthComplete(statusCode: Int) {
        
        if (OnAuthenticationCallback.ERROR_NO_BIOMETRICS == statusCode) {
            //To-DO
        }
    }
    ```