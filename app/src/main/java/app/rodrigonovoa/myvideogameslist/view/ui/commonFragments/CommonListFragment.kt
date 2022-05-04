package app.rodrigonovoa.myvideogameslist.view.ui.commonFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.model.domain.GameListItemResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GamesListResponse
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil
import app.rodrigonovoa.myvideogameslist.view.adapters.CommonListAdapter
import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject

class CommonListFragment : Fragment() {

    val model: CommonListViewModel by inject()
    private val glideUtils: GlideUtils by inject()
    private val dateFormatterUtil: DateFormatterUtil by inject()
    private var listType: String = ""

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tv_common_list_title)
        val recycler = view.findViewById<RecyclerView>(R.id.rc_common_list)
        val imvBack = view.findViewById<ImageView>(R.id.imv_back)

        arguments?.getString("EXTRA_LIST_TYPE")?.let {
            listType = it
        }

        clickListeners(imvBack)
        gameListObserver(recycler)
        setListTitle(tvTitle)
    }

    private fun clickListeners(imvBack:ImageView) {
        imvBack.setOnClickListener {
            removeCurrentFragment()
        }
    }

    private fun removeCurrentFragment() {
        getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
    }

    private fun gameListObserver(recycler: RecyclerView) {
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
                    this
                )
            }
        }
    }

    private fun setListTitle(tvTitle: TextView) {
        when(listType){
            Constants.GAMES_TYPE ->  tvTitle.text = getString(R.string.common_list_title_games)
            Constants.RECORDS_TYPE ->   tvTitle.text = getString(R.string.common_list_title_records)
            Constants.PENDING_TYPE ->   tvTitle.text = getString(R.string.common_list_title_pending)
        }
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun loadData() {
        when(listType){
            Constants.GAMES_TYPE -> setGamesList()
            Constants.RECORDS_TYPE -> setRecordsList()
            Constants.PENDING_TYPE ->  setPendingList()
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun setGamesList(){
        model.getGamesFromRepo()
    }

    private fun setRecordsList(){
        model.getGamesInGameRecordsFromLocalDb()
    }

    private fun setPendingList(){
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