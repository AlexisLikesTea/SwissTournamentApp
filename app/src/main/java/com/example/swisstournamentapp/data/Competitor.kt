package com.example.swisstournamentapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Competitor(val name: String, var points: Double = 0.0, val matchHistory: MutableList<String> = mutableListOf(), var placing: Int = 0): Parcelable
