package com.nyererefy.ui.sheets

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nyererefy.R
import com.nyererefy.utilities.BioListener

class EditBioBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = EditBioBottomSheetFragment()
    }

    private lateinit var listener: BioListener
    private lateinit var progressBar: ProgressBar
    private lateinit var container: LinearLayout
    private var bio: String = ""

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.sheet_edit_bio, null)

        val cancel = view.findViewById<Button>(R.id.cancel)
        val save = view.findViewById<Button>(R.id.save)
        val bioEditText = view.findViewById<EditText>(R.id.bio)

        bioEditText.setText(bio)

        progressBar = view.findViewById(R.id.progressBar)
        container = view.findViewById(R.id.container)

        cancel.setOnClickListener {
            this.dismiss()
        }

        save.setOnClickListener {
            val text = bioEditText.text.toString()

            if (text.isEmpty()) {
                bioEditText.error = getString(R.string.error_empty_bio)
                return@setOnClickListener
            }

            listener.onSaveClicked(text)
        }

        dialog.setContentView(view)
    }

    fun setBio(bio: String?) {
        bio?.let {
            this.bio = bio
        }
    }

    fun setListener(listener: BioListener) {
        this.listener = listener
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        container.visibility = View.GONE
    }
}