package app.rodrigonovoa.myvideogameslist.di


import app.rodrigonovoa.myvideogameslist.ui.gameDetail.GameDetailViewModel
import app.rodrigonovoa.myvideogameslist.ui.sharedFragments.CommonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { CommonListViewModel(get()) }
    viewModel { GameDetailViewModel(get()) }
}
