package idnull.znz.illumination2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import idnull.znz.illumination2.utils.AppPreference
import idnull.znz.illumination2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var mToolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreference.getPreference(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mToolbar = binding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setSupportActionBar(mToolbar)
        title = "Raion"

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.splashFragment) {
                mToolbar.visibility = View.GONE
            }
            else if (destination.id == R.id.loginFragment) {
                mToolbar.visibility = View.GONE
            }
            else {
                mToolbar.visibility = View.VISIBLE
            }
        }
    }
}