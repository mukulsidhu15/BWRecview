package com.example.bwrecv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bwrecv.Constant.Companion.EMAIL
import com.example.bwrecv.Constant.Companion.TOKEN
import com.example.bwrecv.databinding.FragmentOtpBinding
import com.example.bwrecv.util.NetworkCheck
import com.example.bwrecv.util.ProgressBarDialog
import com.example.bwrecv.viewmodel.ViewModel

class OtpFragment : Fragment() {

    private lateinit var viewBinding: FragmentOtpBinding
    private lateinit var viewModel: ViewModel
    private lateinit var sharedPreferences: Preferences
    private lateinit var progressBarDialog: ProgressBarDialog
    private lateinit var network: NetworkCheck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentOtpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        network = NetworkCheck(requireContext())
        sharedPreferences = Preferences(requireContext())
        progressBarDialog = ProgressBarDialog(requireContext())
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.continueBtn.setOnClickListener {
            Helper.hideKeyBoard(view)
            if (network.checkNetwork()){
                val otp = viewBinding.otpET.text
                progressBarDialog.showProgressBar()
                viewModel.otpCode(otp.toString().toInt(), EMAIL)
            }
        }

        otpObserver()
    }

    private fun otpObserver() {

        viewModel.otpResponse.observe(viewLifecycleOwner) {
            progressBarDialog.hideProgressBar()
            if (it.statusCode == 200) {
                TOKEN = it.data.token
                sharedPreferences.saveToken(it.data.token)
                findNavController().popBackStack()
                findNavController().navigate(R.id.priceListFragment)
            }else{
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                   findNavController().navigate(R.id.loginFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }


}