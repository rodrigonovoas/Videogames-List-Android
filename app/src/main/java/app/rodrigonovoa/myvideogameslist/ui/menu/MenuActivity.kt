package app.rodrigonovoa.myvideogameslist.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityMenuBinding
import app.rodrigonovoa.myvideogameslist.ui.sharedFragments.CommonListFragment
import app.rodrigonovoa.myvideogameslist.ui.myList.MyListFragment
import app.rodrigonovoa.myvideogameslist.ui.pendingGames.PendingGamesFragment
import app.rodrigonovoa.myvideogameslist.ui.userOptions.UserOptionsFragment

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragments
        val gamesFragment = CommonListFragment()
        val myListFragment = MyListFragment()
        val pendingGamesFragment = PendingGamesFragment()
        val userOptionsFragment = UserOptionsFragment()

        // view listeners
        binding.llVideogames.setOnClickListener { attachFragment(gamesFragment) }
        binding.llMyList.setOnClickListener { attachFragment(myListFragment) }
        binding.llPendingGames.setOnClickListener { attachFragment(pendingGamesFragment) }
        binding.llUserOptions.setOnClickListener { attachFragment(userOptionsFragment) }
    }

    private fun attachFragment(fragment: Fragment?){
        if (fragment == null) return
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, fragment)
            .commit()
    }
}