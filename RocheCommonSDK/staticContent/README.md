Roche staticContent library
========
This is the staticContent library which provides the functionality to download a zip file and unzip it.

How to use the DownloadStaticContent
----------
- Download static assets and unzips it
    ```
    // retrieves the privacy policy asset URL for version 1.2.1 of EN_US zip file from the manifest.json
    // downloads the privacy policy assets of EN_US zip file, unzips it, and returns the path of the unzipped file/directory
    val path =
        DownloadStaticContent.downloadStaticAssets(
            this@MainActivity,
            "https://passport-static-content.tpp1-dev.platform.navify.com/com.roche.nrm_passport/docs/manifest.json",
            "1.2.1",
            LocaleType.EN_US,
            "privacy_policy",
            ::showProgress
        )

    // optional: display the progress or simply add a log
    private fun showProgress(progress: Int) {
        Log.d("usermanual", "Downloading Progress: $progress")
    }
    ```
How to retrieve a file URL based on the app version, locale and file key
----------
    ```
    val fileUrl = DownloadStaticContent.getUrlFromManifest(context, manifestUrl, appVersion, locale, fileKey)
    ```
How to download a file to the app's files directory
----------
    ```
    val filePath = DownloadStaticContent.downloadFromUrl(context, fileUrl, progress, targetSubDir)
    ```
How to unzip a file to the app's files directory
----------
   ```
   val unzipPath = unzipFile(context, zipPath, subDirPath)
   ```