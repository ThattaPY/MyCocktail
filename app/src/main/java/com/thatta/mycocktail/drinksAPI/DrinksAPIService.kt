package com.thatta.mycocktail.drinksAPI

import com.thatta.mycocktail.models.Drink
import com.thatta.mycocktail.models.Drinks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksAPIService {

    @GET("search.php?")
    suspend fun getDrinks(@Query("f") letter: String): Response<Drinks>

}