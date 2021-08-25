Roche utils library
========
This is the utils library which provides common utilities used in our applications.

This library provide the following utilities.
1. PreferenceUtils
2. UnZipUtils

How to use the PreferenceUtils
----------
- Opens an instance of encrypted SharedPreferences
    ```
    val pref = PreferenceUtils.createOrGetPreference(getApplication(), fileName)
    ```
- Save a value for a key
    ```
    pref.set(key, value)
    ```
- Retrieve a value from a key
    ```
    val value = pref.get(key, "") ?: ""
    ```

How to use the UnZipUtils
----------
- Unzips a zip file from the asset directory to the file directory
    ```
    UnZipUtils.unzipFromAsset("filepath.zip", applicationContext)
    ```
- Unzips a zip file from the asset directory to the file directory followed by the subDirectoryPath
    ```
    // fileDirectory/subDirectoryPath
    UnZipUtils.unzipFromAsset("filepath.zip", applicationContext, "subDirectoryPath")
    ```
- Unzips a zip file from the file directory
    ```
    UnZipUtils.unzipFromAppFiles("filepath.zip", applicationContext)
    ```
- Unzips a zip file from the file directory to the file directory followed by the subDirectoryPath
    ```
    // fileDirectory/subDirectoryPath
    UnZipUtils.unzipFromAppFiles("filepath.zip", applicationContext, "subDirectoryPath")
    ```