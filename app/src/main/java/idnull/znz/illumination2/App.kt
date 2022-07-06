package idnull.znz.illumination2

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import idnull.znz.illumination2.dagger.AppComponent
import idnull.znz.illumination2.dagger.DaggerAppComponent
import javax.inject.Inject

class App : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()


             appComponent = DaggerAppComponent
            .builder()
            .build()


    }
}





