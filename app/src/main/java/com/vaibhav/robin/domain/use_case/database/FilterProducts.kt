package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.data.models.QueryProduct
import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class FilterProducts@Inject constructor(private val repository: DatabaseRepository) {
  //  override operator fun invoke(querry:QueryProduct)=repository.
}
