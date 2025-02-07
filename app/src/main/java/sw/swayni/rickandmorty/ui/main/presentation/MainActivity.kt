package sw.swayni.rickandmorty.ui.main.presentation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import sw.swayni.basemvvm.mvvm.view.BindingActivity
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.databinding.ActivityMainBinding
import sw.swayni.rickandmorty.ui.main.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding, MainViewModel>(layoutId = R.layout.activity_main, MainViewModel::class.java) {
    override fun init() {

        val navHostController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostController.navController
        binding.bottomMenu.setupWithNavController(navController)
    }

    override fun observeData() {

    }
}