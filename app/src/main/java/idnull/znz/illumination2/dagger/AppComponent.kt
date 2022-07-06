package idnull.znz.illumination2.dagger

import dagger.Component
import dagger.android.AndroidInjector
import idnull.znz.illumination2.MainActivity
import idnull.znz.illumination2.dagger.viewmodel.BindsViewModelModule
import idnull.znz.illumination2.dagger.viewmodel.ViewModelModule
import idnull.znz.illumination2.presentation.chat.ChatFragment
import idnull.znz.illumination2.presentation.login.LoginFragment
import idnull.znz.illumination2.presentation.splash.SplashFragment

@Component(modules =  [
    AppModule::class,
    ViewModelModule::class,
    BindsViewModelModule::class])
interface AppComponent {


    fun inject(fragment: LoginFragment)
    fun inject(fragment: ChatFragment)
    fun inject(fragment: SplashFragment)

    @Component.Builder
    interface Builder{

        fun build():AppComponent
    }
}