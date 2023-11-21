package com.ardine.fruturity.di

import com.ardine.fruturity.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}