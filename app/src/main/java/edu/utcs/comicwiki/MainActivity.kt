package edu.utcs.comicwiki

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import edu.utcs.comicwiki.AuthInitActivity.Companion.RC_SIGNIN
import edu.utcs.comicwiki.ui.collection.CollectionFragment
import edu.utcs.comicwiki.ui.creation.CreationFragment
import edu.utcs.comicwiki.ui.creation.CreationFragmentII
import edu.utcs.comicwiki.ui.creation.CreationViewModel
import edu.utcs.comicwiki.ui.home.GenericItemFragment
import edu.utcs.comicwiki.ui.home.HomeFragment
import edu.utcs.comicwiki.ui.posts.PostsFragment
import edu.utcs.comicwiki.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: CreationViewModel by viewModels()

    // a workaround to prevent fragment being killed
//    val a = HomeFragment.newInstance()
//    val b = SearchFragment.newInstance()
//    val c = CreationFragmentII.newInstance()
//    val d = CollectionFragment.newInstance()
//    val e = GenericItemFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // top appBar setup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // bottom appBar setup
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        // overall navigation setup
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_creation,
                R.id.navigation_posts,
//                R.id.navigation_collection
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // setup Authentication
        initAuthListener()
//        a
//        b
//        c
//        d
//        e
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGNIN) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                val header = findViewById<NavigationView>(R.id.side_nav_view).getHeaderView(0)
                val userName = header.findViewById<TextView>(R.id.userName)
                val userEmail = header.findViewById<TextView>(R.id.userEmail)
                val profileImage = header.findViewById<ImageView>(R.id.profileImage)

                logIn.setImageResource(R.mipmap.ic_launcher_round)
                profileImage.setImageResource(R.mipmap.ic_launcher_round)

                userEmail.text = FirebaseAuth.getInstance().currentUser?.email
                userName.text = userEmail.text.toString().substringBefore("@")

                viewModel.getUserComicNodes()
            }

        }
    }

    private fun initAuthListener() {
        // make sure we don't have another user previously signed in
        FirebaseAuth.getInstance().signOut()

        logIn.setOnClickListener {
            val authInitIntent = Intent(this, AuthInitActivity::class.java)
            startActivityForResult(authInitIntent, RC_SIGNIN)
        }

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            viewModel.getUserComicNodes()

            val text = "You have signed out."
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()

            // clear UI
            logIn.setImageResource(R.drawable.ic_baseline_account_circle_30)
            val header = findViewById<NavigationView>(R.id.side_nav_view).getHeaderView(0)
            val profileImage = header.findViewById<ImageView>(R.id.profileImage)
            profileImage.setImageResource(R.drawable.ic_baseline_account_circle_30)
            val userName = header.findViewById<TextView>(R.id.userName)
            val userEmail = header.findViewById<TextView>(R.id.userEmail)
            userEmail.text = "userEmail"
            userName.text = "userName"
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0);
    }
}
