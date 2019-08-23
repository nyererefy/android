package com.nyererefy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.ElectionsRepository
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.Status
import javax.inject.Inject

class ElectionsViewModel
@Inject constructor(repository: ElectionsRepository) : ViewModel() {
    private val _resource = Transformations.map(repository.fetchElections()) { it }

    val data: LiveData<ElectionsQuery.Data> = Transformations.map(_resource) { it.data }
    val status: LiveData<Status> = Transformations.map(_resource) { it.status }
}