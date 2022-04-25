package app.rodrigonovoa.myvideogameslist.view.ui.userOptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.FragmentUserOptionsBinding
import app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences.Prefs
import org.koin.android.ext.android.inject
import org.koin.core.component.getScopeId

class UserOptionsFragment : Fragment() {
    private var _binding: FragmentUserOptionsBinding? = null
    private val binding get() = _binding!!

    private val prefs: Prefs by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtUsernameContent.setText(prefs.username)
        binding.tvGithubRepoContent.text = Constants.GITHUB_REPO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}