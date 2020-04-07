package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.ui.activity.home.HomeActivity
import com.example.vinid_icecreams.ui.activity.login.LoginActivity
import com.example.vinid_icecreams.ui.fragment.event.EventFragment
import com.example.vinid_icecreams.ui.fragment.home.cart.CartFragment
import com.example.vinid_icecreams.ui.fragment.home.details.DetailsFragment
import com.example.vinid_icecreams.ui.fragment.home.map.MapFragment
import com.example.vinid_icecreams.ui.fragment.home.pay.PayFragment
import com.example.vinid_icecreams.ui.fragment.home.requestLocation.RequestLocationFragment
import com.example.vinid_icecreams.ui.fragment.home.shopping.ShoppingFragment
import com.example.vinid_icecreams.ui.fragment.home.store.StoreFragment
import com.example.vinid_icecreams.ui.fragment.login.login.LoginFragment
import com.example.vinid_icecreams.ui.fragment.login.register.RegisterFragment
import com.example.vinid_icecreams.ui.fragment.user.detailsHistory.DetailsHistoryFragment
import com.example.vinid_icecreams.ui.fragment.user.history.HistoryFragment
import com.example.vinid_icecreams.ui.fragment.user.homeUser.UserFragment
import com.example.vinid_icecreams.ui.fragment.user.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ViewBuilder {
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeStoreFragment(): StoreFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeShoppingFragment(): ShoppingFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeRequestPermissionFragment(): RequestLocationFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeMapFragment(): MapFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributePaymentFragment(): PayFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeEventFragment(): EventFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeWalletFragment(): WalletFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeDetailsHistoryFragment(): DetailsHistoryFragment
}