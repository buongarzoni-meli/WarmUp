package com.example.warmup.application

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warmup.model.PokemonApiResponse
import com.example.warmup.model.PokemonResult
import com.example.warmup.services.PokemonApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val service: PokemonApiService = retrofit.create(PokemonApiService::class.java)

    val pokemonList = MutableLiveData<List<PokemonResult>>()

    fun getPokemonList(){
        val call = service.getPokemonList(100, 0)
        call.enqueue(object : Callback<PokemonApiResponse>{
            override fun onResponse(
                call: Call<PokemonApiResponse>,
                response: Response<PokemonApiResponse>
            ) {
                Log.d("CRB", "onResponse")
                response.body()?.results?.let {
                    Log.d("CRB", "postValue")
                    Log.d("CRB", "postValue lista $it")
                    pokemonList.postValue(it)
                }
            }

            override fun onFailure(call: Call<PokemonApiResponse>, t: Throwable) {
                Log.d("CRB", "fallo")
                call.cancel()
            }
        })
    }
}