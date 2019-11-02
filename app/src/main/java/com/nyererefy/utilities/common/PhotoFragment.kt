package com.nyererefy.utilities.common

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.apollographql.apollo.api.FileUpload
import com.nyererefy.utilities.getMimeType
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.jetbrains.anko.support.v4.longToast
import java.io.File


/**
 * Sharing common image cropping functionality
 */
abstract class PhotoFragment : BaseFragment() {
    fun openCropper() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(requireContext(), this)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {

                val uri = result.uri
                val file = File(uri.path)
                val fileUpload = FileUpload(uri.getMimeType(requireContext()),file)

                onImageReady(fileUpload)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                longToast("Error: ${error.localizedMessage}")
            }
        }
    }

    /**
     * Get called when image is ready.
     */
    abstract fun onImageReady(fileUpload: FileUpload)
}