package praonde.com.mercadobitcointeste.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import praonde.com.mercadobitcointeste.exchangeList.data.remote.ExchangeListApi
import praonde.com.mercadobitcointeste.exchangeList.data.remote.ExchangeDataStore
import praonde.com.mercadobitcointeste.exchangeList.data.repository.ExchangeRepositoryImpl
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.viewmodel.ExchangeDetailsScreenViewModel
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel.ExchangeListScreenViewModel
import praonde.com.mercadobitcointeste.retrofit.provideRetrofit
import retrofit2.Retrofit

val appModule = module {
    single { provideRetrofit() }
    single { get<Retrofit>().create(ExchangeListApi::class.java) }
    single { ExchangeDataStore(get()) }
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    viewModel { ExchangeListScreenViewModel(get()) }
    viewModel { ExchangeDetailsScreenViewModel(get()) }
}