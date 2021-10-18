package com.example.warmup.infrastructure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.warmup.application.PokemonInfoViewModel
import com.example.warmup.databinding.ActivityPokemonInfoBinding
import com.squareup.picasso.Picasso

class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonInfoBinding
    lateinit var pokemonInfoViewModel: PokemonInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonInfoViewModel = ViewModelProvider(this).get(PokemonInfoViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        val id = intent.extras?.get("id") as Int
        pokemonInfoViewModel.getPokemonInfo(id)
        pokemonInfoViewModel.pokemonInfo.observe(this, {
            binding.pokemonName.text = "$id - ${it.name}"
            binding.pokemonHeight.text = "Altura: ${it.height/10.0} m"
            binding.pokemonWeight.text = "Peso  : ${it.weight/10.0} kg"
            binding.pokemonType.text = "Tipo   : ${it.types.get(0).type?.name}"
            Picasso.get().load(it.sprites.frontDefault).into(binding.pokemonSprite)
            //Glide.with(this).load(it.sprites.frontDefault).into(binding.imageView)

        })
    }
}