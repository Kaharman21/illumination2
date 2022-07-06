package idnull.znz.illumination2.dagger.viewmodel

import dagger.Binds
import dagger.Module
//возможно не хватает импорта
import androidx.lifecycle.ViewModelProvider
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory


}