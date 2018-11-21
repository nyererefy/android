package com.konektedi.vs.candidates

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.student.grabPreference
import com.konektedi.vs.utilities.common.Constants.CANDIDATE_ID
import com.konektedi.vs.utilities.common.Constants.CLASS_NAME
import com.konektedi.vs.utilities.common.Constants.COVER
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.common.Constants.ID
import com.konektedi.vs.utilities.common.Constants.NAME
import com.konektedi.vs.utilities.common.NetworkState
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.candidate_profile_activity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.longToast
import java.io.File


class Profile : AppCompatActivity() {
    private lateinit var viewModel: CandidatesViewModel
    private var imageUri: Uri? = null
    private var candidateId: Int? = null
    private var electionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidate_profile_activity)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        showDetails()
        change_cover_btn.setOnClickListener { startCropper() }
    }

    private fun startCropper() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            when (resultCode) {
                Activity.RESULT_OK -> {
                    imageUri = result.uri
                    Glide.with(this)
                            .load(File(imageUri!!.path))
                            .into(cover)
                    uploadImage()
                }
                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> longToast(result.error.localizedMessage)
                else -> longToast(getString(R.string.unknown_error))
            }
        }
    }

    private fun uploadImage() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        val file = File(imageUri!!.path)
        builder.addFormDataPart(COVER, file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
        builder.addFormDataPart(CANDIDATE_ID, candidateId.toString())
        builder.addFormDataPart(ELECTION_ID, electionId.toString())

        val requestBody = builder.build()
        viewModel.uploadCover(requestBody).observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    change_cover_btn.visibility = View.GONE
                    change_progress_bar.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {
                    coordinatorLayout.longSnackbar(getString(R.string.cover_changed))
                    change_cover_btn.visibility = View.VISIBLE
                    change_progress_bar.visibility = View.GONE
                }
                else -> {
                    coordinatorLayout.longSnackbar(it?.msg.toString())
                    change_cover_btn.visibility = View.VISIBLE
                    change_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun showDetails() {
        val data = intent.extras

        val coverURL = data!!.getString(COVER)
        candidateId = data.getInt(CANDIDATE_ID)
        electionId = data.getInt(ELECTION_ID)
        val id = data.getInt(ID)
        val name = data.getString(NAME)
        val className = data.getString(CLASS_NAME)

        //Checking if current user is a candidate
        if (id.toString() == grabPreference(this, ID)) {
            change_cover_btn.visibility = View.VISIBLE
        }

        title = name
        schoolView.text = className

        Glide.with(this)
                .load(coverURL)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .dontAnimate()
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover)

        viewModel.getCandidate(candidateId!!).observe(this, Observer {
            it?.details.run {
                val details = it!!.details
                discriptionView.text = details.biography
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }
}
