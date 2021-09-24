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
