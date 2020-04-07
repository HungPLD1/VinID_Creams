package com.example.vinid_icecreams.di.viewModelModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.ui.activity.login.LoginMainViewModel
import com.example.vinid_icecreams.ui.fragment.event.EventViewModel
import com.example.vinid_icecreams.ui.fragment.home.cart.CartViewModel
import com.example.vinid_icecreams.ui.fragment.home.details.DetailsViewModel
import com.example.vinid_icecreams.ui.fragment.home.map.MapViewModel
import com.example.vinid_icecreams.ui.fragment.home.pay.PayViewModel
import com.example.vinid_icecreams.ui.fragment.home.requestLocation.RequestLocationViewModel
import com.example.vinid_icecreams.ui.fragment.home.shopping.ShoppingViewModel
import com.example.vinid_icecreams.ui.fragment.home.store.StoreViewModel
import com.example.vinid_icecreams.ui.fragment.login.login.LoginViewModel
import com.example.vinid_icecreams.ui.fragment.login.register.RegisterViewModel
import com.example.vinid_icecreams.ui.fragment.user.detailsHistory.DetailsHistoryViewModel
import com.example.vinid_icecreams.ui.fragment.user.history.HistoryViewModel
import com.example.vinid_icecreams.ui.fragment.user.homeUser.UserViewModel
import com.example.vinid_icecreams.ui.fragment.user.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    fun bindDataStoreViewModel(
        dataViewModel: StoreViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingViewModel::class)
    fun bindDataShoppingViewModel(
        dataViewModel: ShoppingViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDataDetailsViewModel(
        dataViewModel: DetailsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun binDataHomeViewModel(
        dataViewModel: HomeViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequestLocationViewModel::class)
    fun binRequestLocationViewModel(
        dataViewModel: RequestLocationViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun binDataMapViewModel(
        dataViewModel: MapViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    fun binCartViewModel(
        dataViewModel: CartViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginMainViewModel::class)
    fun binLoginMainViewModel(
        dataViewModel: LoginMainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun binLoginViewModel(
        dataViewModel: LoginViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun binRegisterViewModel(
        dataViewModel: RegisterViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PayViewModel::class)
    fun binPayViewModel(
        dataViewModel: PayViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    fun binEventViewModel(
        dataViewModel: EventViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun binUserViewModel(
        dataViewModel: UserViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    fun binWalletViewModel(
        dataViewModel: WalletViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun binHistoryViewModel(
        dataViewModel: HistoryViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsHistoryViewModel::class)
    fun binDetailsHistoryViewModel(
        dataViewModel: DetailsHistoryViewModel
    ): ViewModel
}
