package com.example.vinid_icecreams.ui.activity.login

import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class LoginMainViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel()