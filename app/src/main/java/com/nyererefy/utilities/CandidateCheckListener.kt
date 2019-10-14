package com.nyererefy.utilities

interface CandidateCheckListener {
    fun onCandidateChecked()
    fun onCandidateConfirmed(candidateId: String, password: String)
}