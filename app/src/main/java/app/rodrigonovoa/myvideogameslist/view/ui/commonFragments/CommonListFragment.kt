package app.rodrigonovoa.myvideogameslist.view.ui.commonFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.model.domain.GameListItemResponse
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import app.rodrigonovoa.myvideogameslist.view.adapters.CommonListAdapter
import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject

class CommonListFragment : Fragment() {

    private val model: CommonListViewModel by inject()
    private val glideUtils: GlideUtils by inject()
    private val dateFormatterUtil: DateFormatterUtil by inject()
    private var listType: String = ""

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tv_common_list_title)
        val recycler = view.findViewById<RecyclerView>(R.id.rc_common_list)

        arguments?.getString("EXTRA_LIST_TYPE")?.let {
            listType = it
        }

        this.model.gamesList.observe(viewLifecycleOwner) { gameList ->
            val games: List<GameListItemResponse> = gameList?.results ?: listOf()
            if(games.isNotEmpty() || model.getPendingGameList().isNotEmpty()){
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.adapter = CommonListAdapter(
                    games,
                    listType,
                    glideUtils,
                    dateFormatterUtil,
                    model.getPendingGameList(),
                    model.getGameCompleteDates(),
                )
            }
        }

        when(listType){
            Constants.GAMES_TYPE -> setGamesList(tvTitle)
            Constants.RECORDS_TYPE -> setRecordsList(tvTitle)
            Constants.PENDING_TYPE ->  setPendingList(tvTitle)
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun setGamesList(tvTitle: TextView){
        tvTitle.text = getString(R.string.common_list_title_games)
        model.getGamesFromRepo()
    }

    private fun setRecordsList(tvTitle: TextView){
        tvTitle.text = getString(R.string.common_list_title_records)
        model.getGamesFromLocalDb()
    }

    private fun setPendingList(tvTitle: TextView){
        tvTitle.text = getString(R.string.common_list_title_pending)
        model.getPendingGamesFromLocalDb()
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
        fun newInstance(type: String) = CommonListFragment().apply {
            arguments = Bundle().apply {
                putString("EXTRA_LIST_TYPE", type)
            }
        }
    }
}