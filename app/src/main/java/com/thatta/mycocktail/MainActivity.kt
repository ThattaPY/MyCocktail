package com.thatta.mycocktail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thatta.mycocktail.databinding.ActivityMainBinding
import com.thatta.mycocktail.drinksAPI.DrinksAPIService
import com.thatta.mycocktail.models.Drink
import com.thatta.mycocktail.viewModel.CocktailRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var drinkList = mutableListOf<Drink>()
    lateinit var mRecyclerView: RecyclerView
    val mAdapter: CocktailRecyclerAdapter = CocktailRecyclerAdapter()
    private lateinit var letter: String
    private var isLoading: Boolean = false
    var indexLetter: Int = 0
    private val letters: List<String> = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i",
        "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "END")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView(drinkList)
        getRetrofit()
        letter = letters[indexLetter]
        getDrinks(letter)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDrinks(letter: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(DrinksAPIService::class.java).getDrinks(letter)
            val drinksList = call.body()
            Log.d("api", "cs: $drinksList")

            runOnUiThread {
                if (call.isSuccessful) {
                    val drink = drinksList?.drinksList ?: emptyList()
                    drinkList.addAll(drink)
                    isLoading = false

                    mAdapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }



    fun setUpRecyclerView(drinks: MutableList<Drink>) {
        mRecyclerView = binding.rvCocktails
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        addOnScrollListener()
        mAdapter.CocktailRecyclerAdapter(drinks, this)
        mRecyclerView.adapter = mAdapter


    }

    private fun addOnScrollListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (!isLoading) {

                        if (!binding.rvCocktails.canScrollVertically(1)) {

                            indexLetter ++
                            isLoading = true
                            letter = letters[indexLetter]
                            if (letter != "END"){
                                getDrinks(letter)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}