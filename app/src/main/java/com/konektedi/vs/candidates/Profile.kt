package com.konektedi.vs.candidates

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.utilities.Constants.*
import com.konektedi.vs.utilities.common.Constants.CLASS_NAME
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.candidate_profile_activity.*
import android.content.Intent
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.konektedi.vs.utilities.common.Constants.CANDIDATE_ID


class Profile : AppCompatActivity() {
    private lateinit var viewModel: CandidatesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidate_profile_activity)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        change_cover.setOnClickListener { uploadCover() }
        showDetails()
    }

    private fun uploadCover() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun showDetails() {
        val data = intent.extras

        val coverURL = data!!.getString(COVER)
        val candidateId = data.getInt(CANDIDATE_ID)
        val name = data.getString(NAME)
        val className = data.getString(CLASS_NAME)

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

        viewModel.getCandidate(candidateId).observe(this, Observer {
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
