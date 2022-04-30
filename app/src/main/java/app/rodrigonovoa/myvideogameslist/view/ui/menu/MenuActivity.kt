package app.rodrigonovoa.myvideogameslist.view.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityMenuBinding
import app.rodrigonovoa.myvideogameslist.view.ui.commonFragments.CommonListFragment
import app.rodrigonovoa.myvideogameslist.view.ui.userOptions.UserOptionsFragment

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragments
        val gamesFragment = CommonListFragment.newInstance(Constants.GAMES_TYPE)
        val myListFragment = CommonListFragment.newInstance(Constants.RECORDS_TYPE)
        val pendingGamesFragment = CommonListFragment.newInstance(Constants.PENDING_TYPE)
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