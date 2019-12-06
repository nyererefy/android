package com.nyererefy.ui.sheets

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nyererefy.R
import com.nyererefy.graphql.CandidatesQuery
import com.nyererefy.graphql.type.VoteInput
import com.nyererefy.utilities.CandidateCheckListener
import com.nyererefy.utilities.htmlText

class ConfirmVotingBottomSheetFragment(
        private val candidate: CandidatesQuery.Candidate,
        private val listener: CandidateCheckListener
) : BottomSheetDialogFragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var container: LinearLayout

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.sheet_confirm_voting, null)

        val msg = view.findViewById<TextView>(R.id.msg)
        val close = view.findViewById<Button>(R.id.close)
        val confirm = view.findViewById<Button>(R.id.confirm)
        val password = view.findViewById<EditText>(R.id.password)
        progressBar = view.findViewById(R.id.progressBar)
        container = view.findViewById(R.id.container)

        val name = "<b>${candidate.user().name()}</b>"

        val msgText = "${getString(R.string.confirm_voting_msg)} ${name}. " +
                getString(R.string.enter_password_to_proceed)

        msg.htmlText(msgText)

        close.setOnClickListener {
            this.dismiss()
        }

        confirm.setOnClickListener {
            val pass = password.text.toString()

            val error = when {
                pass.isEmpty() -> getString(R.string.error_empty_password)
                pass.length != 4 -> getString(R.string.invalid_password)
                else -> null
            }

            if (error != null) {
                password.error = error
                return@setOnClickListener
            }

            val uuid = candidate.uuid()

            if (uuid.isNullOrBlank()) {
                //todo redirect to login mmh!!
                return@setOnClickListener
            }

            val input = VoteInput.builder()
                    .uuid(uuid)
                    .pin(pass)
                    .build()

            listener.onCandidateConfirmed(input)
        }

        dialog.setContentView(view)
    }

    fun show(fragmentManager: FragmentManager) {
        val ft = fragmentManager.beginTransaction()
        ft.add(this, tag)
        ft.commit()
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        container.visibility = View.GONE
    }
}