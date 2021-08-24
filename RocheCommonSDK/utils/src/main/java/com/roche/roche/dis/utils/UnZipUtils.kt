package com.roche.roche.dis.utils

import android.content.Context
import android.util.Log
import org.apache.commons.compress.utils.IOUtils
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * ZipUtils - a utility to unzip a .zip file.
 */
object UnZipUtils {
    private const val TAG = "ZipUtils"

    /**
     * unzip a zip file from the app's assets directory
     * @param filePath the location of the file in the app's assets directory
     * i.e. file.tar.gz
     * i.e. someDirectory/file.tar.gz
     * @param targetDirectory the location of where the files will be unzipped to
     * @param context the application context
     * @return returns true if zip file was successfully unzipped to the app's file directory,
     *  otherwise false
     */
    fun unzipFromAsset(filePath: String, targetDirectory: String, context: Context): Boolean {
        var isSuccess = true
        var inputStream: InputStream? = null
        val destDirectory = getOutputDirectory(targetDirectory, context)
        try {
            inputStream = context.assets.open(filePath)
            generateDirectoryContent(inputStream, destDirectory)
        } catch (e: IOException) {
            isSuccess = false
            when (e) {
                is FileNotFoundException -> {
                    Log.e(TAG, Constants.FILE_NOT_FOUND.plus(": $filePath"), e)
                }
                else -> {
                    Log.e(TAG, "Error while opening tar.gz file!", e)
                }
            }
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                Log.d(TAG, Constants.COULD_NOT_CLOSE_STREAM, e)
            }
        }
        return isSuccess
    }

    /**
     * unzip a zip file from the app's file directory
     * @param filePath the location of the file in the app's file directory
     * i.e. file.tar.gz
     * i.e. someDirectory/file.tar.gz
     * @param targetDirectory the location of where the files will be unzipped to
     * @param context the application context
     * @return returns unzip file path if zip file was successfully unzipped to the app's directory,
     *  otherwise null
     */
    fun unzipFromAppFiles(filePath: String, targetDirectory: String, context: Context): String? {
        val destDirectory = getOutputDirectory(targetDirectory, context)
        var inputStream: FileInputStream? = null
        return try {
            inputStream = FileInputStream(filePath)
            generateDirectoryContent(inputStream, destDirectory)
            destDirectory.path
        } catch (e: FileNotFoundException) {
            Log.e(TAG, Constants.FILE_NOT_FOUND.plus(": $filePath"), e)
            null
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                Log.d(TAG, Constants.COULD_NOT_CLOSE_STREAM, e)
            }
        }
    }

    /**
     * create directories and files from an InputStream to the specified directory
     * @param inputStream the zipped file's content's input stream of bytes
     * @param destDirectory the destination directory of where to create the directories/files
     * @return returns true if directories and files from InputStream was successfully created to
     *  the app's file directory, otherwise false
     */
    private fun generateDirectoryContent(inputStream: InputStream, destDirectory: File): Boolean {
        var isSuccess = true

        var zipStream: ZipInputStream? = null
        try {
            zipStream = ZipInputStream(inputStream)
            var zipEntry: ZipEntry?

            while (zipStream.nextEntry.also { zipEntry = it } != null) {
                if (zipEntry!!.isDirectory || zipEntry!!.name.contains("/.")) {
                    continue
                } else {
                    createFile(zipStream, zipEntry!!, destDirectory)
                }
            }
        } catch (e: IOException) {
            isSuccess = false
            Log.e(TAG, Constants.UNZIPPING_ERROR, e)
        } finally {
            try {
                zipStream?.close()
            } catch (e: IOException) {
                Log.d(TAG, Constants.COULD_NOT_CLOSE_STREAM, e)
            }
        }
        return isSuccess
    }

    /**
     * create the file to the target location
     */
    private fun createFile(zipStream: ZipInputStream, zipEntry: ZipEntry, destDirectory: File) {
        val outputFile =
            File(destDirectory.toString() + File.separator + zipEntry.name)
        outputFile.parentFile?.mkdirs()
        IOUtils.copy(zipStream, FileOutputStream(outputFile))
        zipStream.closeEntry()
    }

    /**
     * creates the directory in which the files will be copied too
     */
    private fun getOutputDirectory(targetDirectory: String, context: Context): File {
        var destDirectory: File = context.filesDir
        destDirectory = File(destDirectory.toString() + File.separator + targetDirectory)
        destDirectory.mkdirs()
        return destDirectory
    }
}