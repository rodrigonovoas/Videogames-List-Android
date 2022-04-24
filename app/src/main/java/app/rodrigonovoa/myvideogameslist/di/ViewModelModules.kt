package app.rodrigonovoa.myvideogameslist.di


import app.rodrigonovoa.myvideogameslist.ui.addRecord.AddRecordViewModel
import app.rodrigonovoa.myvideogameslist.ui.gameDetail.GameDetailViewModel
import app.rodrigonovoa.myvideogameslist.ui.commonFragments.CommonListViewModel
import app.rodrigonovoa.myvideogameslist.ui.recordDetail.RecordDetailViewModel
import app.rodrigonovoa.myvideogameslist.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { CommonListViewModel(get(), get()) }
    viewModel { GameDetailViewModel(get()) }
    viewModel { AddRecordViewModel(get(), get()) }
    viewModel { RecordDetailViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}
