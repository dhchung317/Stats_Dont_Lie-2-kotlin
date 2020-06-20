package com.example.statsdontlie.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders

class BDLViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        fun getInstance(fragment: Fragment?): BDLViewModel {
            return ViewModelProviders.of(fragment!!).get(BDLViewModel::class.java)
        }

        fun getInstance(activity: AppCompatActivity?): BDLViewModel {
            return ViewModelProviders.of(activity!!).get(BDLViewModel::class.java)
        }
    }
}