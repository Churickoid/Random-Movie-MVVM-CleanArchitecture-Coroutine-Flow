package com.example.randommovie.presentation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.presentation.tools.AppBarActions


class MainActivity : AppCompatActivity(), AppBarActions {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


       /* val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.filterFragment, R.id.listFragment, R.id.movieFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)*/


        viewModel.color.observe(this){(color, colorLight, colorDark) ->
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
            window.statusBarColor = colorDark
        }

    }

    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/

    override fun changeTitle(label: String) {
        supportActionBar?.title = label
    }

    override fun changeColor(color: Int, colorLight: Int, colorDark: Int) {
        viewModel.setColor(color,colorLight,colorDark)
    }
}