package com.example.bwrecv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bwrecv.databinding.FragmentLoginBinding
import com.example.bwrecv.util.NetworkCheck
import com.example.bwrecv.util.ProgressBarDialog
import com.example.bwrecv.viewmodel.ViewModel

class LoginFragment : Fragment() {

    private lateinit var viewBinding: FragmentLoginBinding
    private lateinit var viewModel: ViewModel
    private lateinit var progressBarDialog: ProgressBarDialog
    private lateinit var network: NetworkCheck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        progressBarDialog = ProgressBarDialog(requireContext())
        network = NetworkCheck(requireContext())
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().popBackStack()

        viewBinding.continueBtn.setOnClickListener {
            Helper.hideKeyBoard(view)

            val email = viewBinding.emailET.text
            if (Helper.isValidEmail(email.toString()) && Helper.isValidEmailFormat(email.toString())){
                if (network.checkNetwork()){
                    Constant.EMAIL = email.toString()
                    progressBarDialog.showProgressBar()
                    viewModel.login(email.toString())
                }else{
                    Toast.makeText(requireContext(),"No Internet.", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(),"Please enter valid email.", Toast.LENGTH_SHORT).show()
            }

        }

        loginObserver()
    }

    private fun loginObserver(){
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            progressBarDialog.hideProgressBar()
            if (it.statusCode==200){
                try {
                    findNavController().navigate(R.id.otpFragment)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }else{
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }


}