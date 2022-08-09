package com.vaibhav.robin.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

object RobinVectorLoader {
    private val robinVectorList = listOf(
        Icons.Filled.TrendingUp, //0
        Icons.Filled.Book, //1
        Icons.Filled.Restaurant//2
    )
    fun load(int: Int): ImageVector? {
        if ( int>=0)
            return if(robinVectorList[int].equals(null))
                null
            else robinVectorList[int]
        return null
    }
}