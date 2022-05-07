package app.rodrigonovoa.myvideogameslist.view.ui.userOptions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.databinding.FragmentUserOptionsBinding
import app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences.Prefs
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserOptionsFragment : Fragment() {
    private var _binding: FragmentUserOptionsBinding? = null
    private val binding get() = _binding!!

    private val prefs: Prefs by inject()
    private val model: UserOptionsViewModel by viewModel()

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
        binding.swSkipSplash.isChecked = prefs.skip_splash

        binding.swSkipSplash.setOnCheckedChangeListener { _, isChecked ->
            prefs.skip_splash = isChecked
        }

        binding.tvGithubRepo.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_REPO))
            startActivity(browserIntent)
        }

        binding.imvChange.setOnClickListener {
            model.updateUsername(binding.edtUsernameContent.text.toString(), 1)
        }

        binding.imvBack.setOnClickListener {
            removeCurrentFragment()
        }
    }

    private fun removeCurrentFragment() {
        getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}