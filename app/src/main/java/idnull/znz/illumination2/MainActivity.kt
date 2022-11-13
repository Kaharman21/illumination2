package idnull.znz.illumination2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import idnull.znz.illumination2.utils.AppPreference
import idnull.znz.illumination2.databinding.ActivityMainBinding
import idnull.znz.illumination2.domain.FireBaseInit

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var mToolbar: Toolbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreference.getPreference(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavView = findViewById(R.id.bottomNavView)

        mToolbar = binding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottomNavView.setupWithNavController(navController)

        setSupportActionBar(mToolbar)
//        title = "Raion"

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.splashFragment) {
                mToolbar.visibility = View.GONE
                bottomNavView.visibility = View.GONE
            }
            else if (destination.id == R.id.loginFragment) {
                mToolbar.visibility = View.GONE
                bottomNavView.visibility = View.GONE
            }
            else if (destination.id == R.id.chatFragment) {
                mToolbar.visibility = View.VISIBLE
                bottomNavView.visibility = View.VISIBLE
                mToolbar.title = "Chat"
            }
            else if (destination.id == R.id.clanFragment) {
                mToolbar.visibility = View.VISIBLE
                bottomNavView.visibility = View.VISIBLE
                mToolbar.title = "Clan"
            }
        }
    }
}