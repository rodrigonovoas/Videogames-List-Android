package app.rodrigonovoa.myvideogameslist.di


import app.rodrigonovoa.myvideogameslist.view.ui.addPendingGame.AddPendingGameViewModel
import app.rodrigonovoa.myvideogameslist.view.ui.addRecord.AddRecordViewModel
import app.rodrigonovoa.myvideogameslist.view.ui.gameDetail.GameDetailViewModel
import app.rodrigonovoa.myvideogameslist.view.ui.commonFragments.CommonListViewModel
import app.rodrigonovoa.myvideogameslist.view.ui.recordDetail.RecordDetailViewModel
import app.rodrigonovoa.myvideogameslist.view.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { CommonListViewModel(get(), get(), get()) }
    viewModel { GameDetailViewModel(get()) }
    viewModel { AddRecordViewModel(get(), get()) }
    viewModel { RecordDetailViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { AddPendingGameViewModel(get(), get()) }
}
