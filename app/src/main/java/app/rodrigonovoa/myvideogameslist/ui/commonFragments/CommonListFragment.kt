package app.rodrigonovoa.myvideogameslist.ui.commonFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.ui.adapters.CommonListAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject

class CommonListFragment : Fragment() {

    private val model: CommonListViewModel by inject()
    private var isGamesList = true

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.rc_common_list)

        arguments?.getBoolean("EXTRA_GAME_LIST")?.let {
            isGamesList = it
        }

        this.model.gamesList.observe(viewLifecycleOwner) { gameList ->
            val games: List<GameResponse> = gameList?.results ?: listOf()
            if(games.size > 0){
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.adapter = CommonListAdapter(games, isGamesList)
            }
        }

        if(isGamesList){
            model.getGamesFromRepo()
        }else{
            model.getGamesFromLocalDb()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(gameList: Boolean) = CommonListFragment().apply {
            arguments = Bundle().apply {
                putBoolean("EXTRA_GAME_LIST", gameList)
            }
        }
    }
}