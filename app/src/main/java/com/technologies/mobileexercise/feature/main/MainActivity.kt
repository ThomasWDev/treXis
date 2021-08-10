package com.technologies.mobileexercise.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseActivity
import com.technologies.mobileexercise.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(toolbar)
    }
}