//package com.nyererefy.ui.fragments
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.MenuItem
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.request.RequestOptions
//import com.nyererefy.R
//import com.nyererefy.utilities.grabPreference
//import com.nyererefy.utilities.common.Constants.BIOGRAPHY
//import com.nyererefy.utilities.common.Constants.CANDIDATE_ID
//import com.nyererefy.utilities.common.Constants.COVER
//import com.nyererefy.utilities.common.Constants.ELECTION_ID
//import com.nyererefy.utilities.common.Constants.ID
//import com.nyererefy.utilities.common.NetworkState
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
//import kotlinx.android.synthetic.main.candidate_profile_activity.*
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import org.jetbrains.anko.design.longSnackbar
//import org.jetbrains.anko.longToast
//import java.io.File
//
//
//class ProfileActivity : AppCompatActivity() {
//    private lateinit var viewModel: CandidatesViewModel
//    private var imageUri: Uri? = null
//    private var candidateId: Int = 0
//    private var electionId: Int = 0
//    private var id: Int = 0
//    private var bioTexts = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.candidate_profile_activity)
//
//        setSupportActionBar(toolbar)
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)
//
//        showDetails()
//        initListeners()
//    }
//
//    private fun initListeners() {
//        change_cover_btn.setOnClickListener { startCropper() }
//        change_bio.setOnClickListener {
//            change_bio.visibility = View.GONE
//            save_bio.visibility = View.VISIBLE
//
//            bio_view.visibility = View.GONE
//            bio_input.visibility = View.VISIBLE
//        }
//        save_bio.setOnClickListener { postBio() }
//    }
//
//    private fun startCropper() {
//        // start picker to get image for cropping and then use the image in cropping activity
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1, 1)
//                .start(this)
//    }
//
//    public override fun onActivityResult(requestCode: Int, resultCode: Int, elections: Intent?) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(elections)
//            when (resultCode) {
//                Activity.RESULT_OK -> {
//                    imageUri = result.uri
//                    Glide.with(this)
//                            .load(File(imageUri!!.path))
//                            .into(cover)
//                    uploadImage()
//                }
//                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> longToast(result.error.localizedMessage)
//                else -> longToast(getString(R.string.unknown_error))
//            }
//        }
//    }
//
//    private fun uploadImage() {
//        val builder = MultipartBody.Builder()
//        builder.setType(MultipartBody.FORM)
//
//        val file = File(imageUri!!.path)
//        builder.addFormDataPart(COVER, file.name, RequestBody.create(MediaType.parse("multipart/form-elections"), file))
//        builder.addFormDataPart(CANDIDATE_ID, candidateId.toString())
//        builder.addFormDataPart(ELECTION_ID, electionId.toString())
//
//        val requestBody = builder.build()
//        viewModel.uploadCover(requestBody).observe(this, Observer {
//            when (it) {
//                NetworkState.LOADING -> {
//                    change_progress_bar.visibility = View.VISIBLE
//                }
//                NetworkState.LOADED -> {
//                    coordinatorLayout.longSnackbar(getString(R.string.cover_changed))
//                    change_progress_bar.visibility = View.GONE
//                }
//                else -> {
//                    coordinatorLayout.longSnackbar(it?.msg.toString())
//                    change_progress_bar.visibility = View.GONE
//                }
//            }
//        })
//    }
//
//    private fun showDetails() {
//        val elections = intent.extras
//
//        candidateId = elections!!.getInt(CANDIDATE_ID)
//        id = elections.getInt(ID)
//        electionId = elections.getInt(ELECTION_ID)
//
//        val coverURL = elections.getString(COVER)
//
//        if (id.toString() != grabPreference(this, ID)) {
//            change_cover_btn.visibility = View.GONE
//            change_bio.visibility = View.GONE
//        }
//
//        Glide.with(this)
//                .load(coverURL)
//                .apply(RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                        .dontAnimate()
//                        .placeholder(R.drawable.holder)
//                        .error(R.drawable.holder))
//                .into(cover)
//
//        viewModel.getCandidate(candidateId).observe(this, Observer {
//            val details = it.details
//            bioTexts = details.biography.toString()
//            username_view.text = details.username
//
//            name_view.text = details.name
//
//            val schoolText = "${details.abbr} ${details.year}"
//            school_view.text = schoolText
//
//            bio_view.text = details.biography
//            bio_input.setText(bioTexts)
//        })
//
//        viewModel.networkState.observe(this, Observer {
//            when (it) {
//                NetworkState.LOADING -> progress_bar.visibility = View.VISIBLE
//                NetworkState.LOADED -> progress_bar.visibility = View.GONE
//                else -> coordinatorLayout.longSnackbar(it.msg.toString())
//            }
//        })
//    }
//
//    private fun postBio() {
//        val bioInput = bio_input.text.toString()
//
//        val map = mapOf(
//                CANDIDATE_ID to candidateId.toString(),
//                BIOGRAPHY to bioInput
//        )
//
//        viewModel.updateBiography(map).observe(this, Observer {
//            when (it) {
//                NetworkState.LOADING -> save_bio.visibility = View.GONE
//                NetworkState.LOADED -> {
//                    change_bio.visibility = View.VISIBLE
//                    save_bio.visibility = View.GONE
//
//                    bio_input.visibility = View.GONE
//                    bio_view.visibility = View.VISIBLE
//
//                    bio_view.text = bioInput
//                    coordinatorLayout.longSnackbar(R.string.bio_updated)
//                }
//                else -> {
//                    save_bio.visibility = View.VISIBLE
//                    coordinatorLayout.longSnackbar(it.msg.toString())
//                }
//            }
//        })
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> super.onBackPressed()
//        }
//        return true
//    }
//
//}
