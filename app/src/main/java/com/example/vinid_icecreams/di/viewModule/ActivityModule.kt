package com.example.vinid_icecreams.di.viewModule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.di.viewModelModule.ViewModelKey
import com.example.vinid_icecreams.view.activity.HomeActivity
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ActivityModule {

    @Binds
    fun bindMainActivity(activity: HomeActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelIceCream::class)
    fun bindDataViewModel(
        dataViewModel: ViewModelIceCream
    ): ViewModel

}