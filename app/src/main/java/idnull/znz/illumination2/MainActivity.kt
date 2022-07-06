package idnull.znz.illumination2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import idnull.znz.illumination2.utils.APP_ACTIVITY
import idnull.znz.illumination2.utils.AppPreference
import idnull.znz.illumination2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mToolbar: Toolbar
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        //       App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        AppPreference.getPreference(this)
        APP_ACTIVITY = this

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mToolbar = mBinding.toolbar
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }


}