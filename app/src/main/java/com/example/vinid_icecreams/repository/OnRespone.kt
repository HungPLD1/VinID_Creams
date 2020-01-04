package com.example.vinid_icecreams.repository

interface OnRespone<T,K> {
    fun onSuccess(value: T)
    fun onFailse(value: K)
}