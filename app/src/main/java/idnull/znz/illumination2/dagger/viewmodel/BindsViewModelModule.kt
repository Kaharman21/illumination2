package idnull.znz.illumination2.dagger.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import idnull.znz.illumination2.presentation.chat.ChatViewModel
import idnull.znz.illumination2.presentation.login.LoginViewModel
import idnull.znz.illumination2.presentation.splash.SplashViewModel


@Module
abstract class BindsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindsLoginViewModel(viewModel: LoginViewModel):ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    internal abstract fun bindsChatViewModel(viewModel: ChatViewModel):ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindsSplashViewModel(viewModel: SplashViewModel):ViewModel

}