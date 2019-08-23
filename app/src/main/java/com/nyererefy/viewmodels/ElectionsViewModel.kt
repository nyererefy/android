package com.nyererefy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nyererefy.data.ElectionsRepository
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.Resource
import javax.inject.Inject

class ElectionsViewModel
@Inject constructor(private val repository: ElectionsRepository) : ViewModel() {

    val elections: LiveData<Resource<ElectionsQuery.Data>>
        get() {
            return repository.fetchElections()
        }
}