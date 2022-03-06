package com.thatta.mycocktail.models

import com.google.gson.annotations.SerializedName

data class Drink(@SerializedName("idDrink") var id: String,
                 @SerializedName("strDrink") var name: String,
                 @SerializedName("strIngredient1") var alcohol: String,
                 @SerializedName("strDrinkThumb") var thumbUrl: String)
