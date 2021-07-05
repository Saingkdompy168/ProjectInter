package com.chipmong.dms.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.projectinter.extensions.isCameraAndStoragePermissionGrant
import com.example.projectinter.extensions.isReadExternalPermission
import com.example.projectinter.extensions.requestCameraAndStoragePermission
import com.example.projectinter.extensions.requestReadStoragePermission
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 01-Mar-20
 */
object MediaManager {

    fun pickPhoto(context: Activity, title: String, requestCode: Int) {
        if (context.isReadExternalPermission()) {
            onOpenPhotoFromGallery(context, title, requestCode)
        } else {
            context.requestReadStoragePermission(requestCode)
        }
    }

    fun pickPhotoVideo(activity: Activity, title: String, requestCode: Int) {
        if (activity.isReadExternalPermission()) {
            onOpenPhotoVideoFromGallery(activity, title, requestCode)
        } else {
            activity.requestReadStoragePermission(requestCode)
        }
    }

    private fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
    }
//    fun data(context: Activity){
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//        val path = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//            "My Application Directory"
//        )
//
//        if (!path.exists()) {
//            path.mkdir()
//        }
//
//        val imageFile = File.createTempFile("Your file Name", ".jpg", path)
//
//        val uri = FileProvider.getUriForFile(
//            context,
//            context.applicationContext.packageName + ".provider",
//            imageFile
//        )
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//        val resInfoList = context.packageManager
//            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        for (resolveInfo in resInfoList) {
//            val packageName = resolveInfo.activityInfo.packageName
//            context.grantUriPermission(
//                packageName,
//                CurrentUri,
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
//            )
//        }
//
//        startActivityForResult(intent, IMAGE_CAPTURE) // IMAGE_CAPTURE = 0
//
//    }

    fun dispatchTakePictureIntent(context: Activity, requestCode: Int): File? {
        if (context.isCameraAndStoragePermissionGrant()) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                // Create the File where the photo should go
                val photoFile = createImageFile(context)
                // Continue only if the File was successfully created
//                val photoURI = FileProvider.getUriForFile(
//                    context,
//                    com.chipmong.dms.Constants.FILE_PROVIDER,
//                    photoFile
//                )
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK)
//                takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 2);
//                takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                context.startActivityForResult(takePictureIntent, requestCode)
                return photoFile
            }
        } else {
            context.requestCameraAndStoragePermission(requestCode)
        }
        return null
    }

    fun performCrop(
        activity: Activity,
        picUri: Uri,
        requestCode: Int,
        aspectX: Int? = null,
        aspectY: Int? = null, sizeX: Int = 512, sizeY: Int = 512
    ): File? {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*")
            // set crop properties here
            cropIntent.putExtra("crop", true)
            // indicate aspect of desired crop
            aspectX?.let {
                cropIntent.putExtra("aspectX", it)
            }
            aspectY?.let {
                cropIntent.putExtra("aspectY", it)
            }
//
            // indicate output X and Y
            cropIntent.putExtra("outputX", sizeX)
            cropIntent.putExtra("outputY", sizeY)
//            val file = File(Environment.getExternalStorageDirectory(), "${System.currentTimeMillis()}.jpg")
            val file = File(
                activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "${System.currentTimeMillis()}.jpg"
            )
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
            // retrieve data on return
            cropIntent.putExtra("return-data", false)
            // start the activity - we handle returning in onActivityResult
            activity.startActivityForResult(cropIntent, requestCode)
            return file
        } catch (anfe: ActivityNotFoundException) {
            // display an e message
            val errorMessage = "Whoops - your device doesn't support the crop action!"
            val toast = Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
            return null
        }
    }

    fun performCropCover(activity: Activity, picUri: Uri, requestCode: Int): File? {
        return performCrop(activity, picUri, requestCode, 2, 1, 1024, 512)
    }

    private fun onOpenPhotoFromGallery(
        mContext: Context,
        myScreenTitle: String,
        myIntentRequestCode: Int
    ) {
        onOpenMediaGallery(mContext, myScreenTitle, myIntentRequestCode, "image/*")
    }

    private fun onOpenPhotoVideoFromGallery(
        mContext: Context,
        myScreenTitle: String,
        myIntentRequestCode: Int
    ) {
        onOpenMediaGallery(mContext, myScreenTitle, myIntentRequestCode, "video/*")
    }

    private fun onOpenMediaGallery(
        mContext: Context,
        myScreenTitle: String,
        myIntentRequestCode: Int,
        mediaType: String
    ) {
        try {
            val mIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            mIntent.type = mediaType
            (mContext as Activity).startActivityForResult(
                Intent.createChooser(
                    mIntent,
                    myScreenTitle
                ), myIntentRequestCode
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * @param contentURI URI
     * @return Real Path
     */
    fun onGetRealPathImageFromURI(mContext: Context, contentURI: String): String? {
        val contentUri = Uri.parse(contentURI)
        val cursor = mContext.contentResolver.query(contentUri, null, null, null, null)

        if (cursor == null) {
            return contentUri.path
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(index)
        }
    }

    fun onGetRealPathImage(mActivity: Activity, contentURI: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        var cursorLoader: Cursor? = null
        try {
            cursorLoader = mActivity.contentResolver.query(
                contentURI, proj, null, null, null
            )
            if (cursorLoader != null && cursorLoader.moveToFirst()) {
                val columnIndex = cursorLoader.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                result = cursorLoader.getString(columnIndex)
            }
        } finally {
            cursorLoader?.close()
        }

        return result
    }

}