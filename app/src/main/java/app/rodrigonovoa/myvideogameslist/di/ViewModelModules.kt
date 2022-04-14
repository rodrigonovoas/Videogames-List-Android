package app.rodrigonovoa.myvideogameslist.di


import app.rodrigonovoa.myvideogameslist.ui.fragments.CommonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { CommonListViewModel(get()) }
}
