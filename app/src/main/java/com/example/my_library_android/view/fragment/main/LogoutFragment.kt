package com.example.my_library_android.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.my_library_android.R
import com.example.my_library_android.databinding.FragmentLogoutBinding
import com.example.my_library_android.model.SessionManager
import com.example.my_library_android.model.data.ExecutionResult
import com.example.my_library_android.util.AlertDialogUtils
import com.example.my_library_android.view.activity.LoginActivity
import com.example.my_library_android.viewmodel.fragment.LogoutViewModel

class LogoutFragment : Fragment(R.layout.fragment_logout) {

    private val logoutViewModel: LogoutViewModel by viewModels()
    private var _binding: FragmentLogoutBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLogoutBinding.bind(view)

        SessionManager.setupSharedPreferences(requireContext())

        binding.logoutButton.setOnClickListener {
            logoutViewModel.logout()
        }

        logoutViewModel.result.observe(viewLifecycleOwner) { result ->
            showLogin(result)
        }
    }

    private fun showLogin(result: ExecutionResult) {
        if (result.isSuccess) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        } else {
            AlertDialogUtils.showAlertDialog(requireContext(), childFragmentManager, result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
