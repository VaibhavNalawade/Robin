package com.vaibhav.robin.presentation.handler

import com.vaibhav.robin.presentation.ErrorResolutionPolicy
import com.vaibhav.robin.presentation.ErrorVisualsType
import com.vaibhav.robin.presentation.UiText

interface RobinErrorHandler {
    fun getTitle():UiText
    fun getMessage():UiText
    fun getErrorCode():UiText? = null
    fun getErrorResolutionPolicy(): ErrorResolutionPolicy
    fun getErrorVisualType():ErrorVisualsType?
}