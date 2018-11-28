package com.konektedi.vs.candidates

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
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


class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: CandidatesViewModel
    private var imageUri: Uri? = null
    private var candidateId: Int = 0
    private var electionId: Int = 0
    private var id: Int = 0
    private var bioTexts = ""
//    private lateinit var thisMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidate_profile_activity)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        showDetails()
        initListeners()
    }

    private fun initListeners() {
        change_cover_btn.setOnClickListener { startCropper() }
        change_bio.setOnClickListener {
            change_bio.visibility = View.GONE
            save_bio.visibility = View.VISIBLE

            bio_view.visibility = View.GONE
            bio_input.visibility = View.VISIBLE
        }
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
                    change_progress_bar.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {
                    coordinatorLayout.longSnackbar(getString(R.string.cover_changed))
                    change_progress_bar.visibility = View.GONE
                }
                else -> {
                    coordinatorLayout.longSnackbar(it?.msg.toString())
                    change_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun showDetails() {
        val data = intent.extras

        candidateId = data!!.getInt(CANDIDATE_ID)
        id = data.getInt(ID)
        electionId = data.getInt(ELECTION_ID)

        val name = data.getString(NAME)
        val coverURL = data.getString(COVER)

        if (id.toString() != grabPreference(this, ID)) {
            change_cover_btn.visibility = View.GONE
            change_bio.visibility = View.GONE
        }

        name_view.text = name

        Glide.with(this)
                .load(coverURL)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .dontAnimate()
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover)

        viewModel.getCandidate(candidateId).observe(this, Observer {
            val details = it.details
            bioTexts = details.biography.toString()
            username_view.text = details.username

            val schoolText = "${details.abbr} ${details.year}"
            school_view.text = schoolText

            bio_view.text = details.biography
            bio_input.setText(bioTexts)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
//            R.id.change_dp -> startCropper()
//            R.id.edit_bio -> {
//                editBio(item)
//            }
//            R.id.save_bio -> {
//                saveBio(item)
//            }
        }
        return true
    }

//    private fun editBio(item: MenuItem) {
//        item.isVisible = false
//        thisMenu.findItem(R.id.save_bio).isVisible = true
//
//        bio_input.visibility = View.VISIBLE
//        discriptionView.visibility = View.GONE
//    }

//    private fun saveBio(item: MenuItem) {
////        item.isVisible = false
////        thisMenu.findItem(R.id.edit_bio).isVisible = true
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        //Checking if current user is a candidate
//        if (id.toString() == grabPreference(this, ID)) {
//            menuInflater.inflate(R.menu.candidate_activity_menu, menu)
//            thisMenu = menu
//        }
//        return super.onCreateOptionsMenu(menu)
//    }
}
