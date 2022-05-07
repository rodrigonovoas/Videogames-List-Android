package app.rodrigonovoa.myvideogameslist.view.ui.commonFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
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
import timber.log.Timber

class CommonListFragment : Fragment() {

    val model: CommonListViewModel by inject()
    private val glideUtils: GlideUtils by inject()
    private val dateFormatterUtil: DateFormatterUtil by inject()
    private var listType: String = ""

    // VIEWS
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tv_common_list_title)
        val imvBack = view.findViewById<ImageView>(R.id.imv_back)
        val imvSearch = view.findViewById<ImageView>(R.id.imv_search)
        val edtQuery = view.findViewById<EditText>(R.id.edt_game_filter)
        recycler = view.findViewById<RecyclerView>(R.id.rc_common_list)
        progressBar = view.findViewById<ProgressBar>(R.id.pb_list)

        arguments?.getString("EXTRA_LIST_TYPE")?.let {
            listType = it
        }

        clickListeners(imvBack, imvSearch, edtQuery)
        gameListObserver(recycler)
        setListTitle(tvTitle)
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun clickListeners(imvBack:ImageView, imvSearch: ImageView, edtQuery: EditText) {
        imvBack.setOnClickListener {
            removeCurrentFragment()
        }

        imvSearch.setOnClickListener {
            if(edtQuery.text.toString().isNotEmpty()){
                val query = edtQuery.text.toString()
                model.getGamesByQueryFromRepo(query)
            }else{
                clearAdapter()
            }
        }

    }

    private fun removeCurrentFragment() {
        getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
    }

    private fun gameListObserver(recycler: RecyclerView) {
        this.model.gamesList.observe(viewLifecycleOwner) { gameList ->
            val games: List<GameListItemResponse> = gameList?.results ?: listOf()
            recycler.layoutManager = LinearLayoutManager(context)
            if(games.isNotEmpty() || model.getPendingGameList().isNotEmpty()){
                recycler.adapter = CommonListAdapter(
                    games,
                    listType,
                    glideUtils,
                    dateFormatterUtil,
                    model.getPendingGameList(),
                    model.getGameCompleteDates(),
                    this
                )
            }else{
                if(model.getPendingGameList().isEmpty()){
                    clearAdapter()
                }
            }

            progressBar.visibility = View.GONE
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
        progressBar.visibility = View.VISIBLE
        clearAdapter()
        when(listType){
            Constants.GAMES_TYPE -> setGamesList()
            Constants.RECORDS_TYPE -> setRecordsList()
            Constants.PENDING_TYPE ->  setPendingList()
        }
    }

    private fun clearAdapter(){
        if(recycler != null && recycler.adapter != null){
            recycler?.adapter =  CommonListAdapter(
                listOf(),
                listType,
                glideUtils,
                dateFormatterUtil,
                listOf(),
                listOf(),
                this
            )
        }

        progressBar.visibility = View.VISIBLE
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