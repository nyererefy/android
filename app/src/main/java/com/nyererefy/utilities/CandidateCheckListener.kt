package com.nyererefy.utilities

import com.nyererefy.graphql.type.VoteInput

interface CandidateCheckListener {
    fun onCandidateChecked()
    fun onCandidateConfirmed(input: VoteInput)
}