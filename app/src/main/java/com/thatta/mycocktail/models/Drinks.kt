package com.thatta.mycocktail.models

import com.google.gson.annotations.SerializedName

data class Drinks(@SerializedName("drinks") var drinksList: List<Drink>)
