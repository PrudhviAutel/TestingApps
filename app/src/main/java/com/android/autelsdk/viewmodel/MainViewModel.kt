package com.example.myapplicationdragonfish.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val listOfSdk = ArrayList<String>()
    init {
        listOfSdk.add("DragonFish SDK")
        listOfSdk.add("Arowana SDK")
        listOfSdk.add("Upcoming...")

    }
}