package com.example.warmup.infrastructure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warmup.application.PokemonListViewModel
import com.example.warmup.databinding.ActivityPokemonListBinding

class PokemonListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonListBinding

    private lateinit var pokemonListViewModel: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonListViewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        binding.pokemonListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pokemonListRecyclerView.adapter = PokemonListAdapter{
            val intent = Intent(this, PokemonInfoActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        pokemonListViewModel.getPokemonList()
        Log.d("CRB", "initUI hizo el get")
        pokemonListViewModel.pokemonList.observe(this, {
            Log.d("CRB", "initUI agrego la lista $it")
            (binding.pokemonListRecyclerView.adapter as PokemonListAdapter).setData(it)
        })

    }
}