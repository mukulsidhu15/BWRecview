package com.example.bwrecv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bwrecv.databinding.FragmentPriceListBinding
import com.example.bwrecv.model.login.DataXX
import com.example.bwrecv.util.NetworkCheck
import com.example.bwrecv.util.ProgressBarDialog
import com.example.bwrecv.viewmodel.ViewModel

class PriceListFragment : Fragment() {

    private lateinit var viewBinging: FragmentPriceListBinding
    private lateinit var viewModel: ViewModel
    private lateinit var progressBarDialog: ProgressBarDialog
    private lateinit var network: NetworkCheck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinging = FragmentPriceListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        network = NetworkCheck(requireContext())
        progressBarDialog = ProgressBarDialog(requireContext())
        val layoutManager = LinearLayoutManager(context)
        viewBinging.priceListRecv.layoutManager = layoutManager
        return viewBinging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                setToAdapter(it[0].priceList)
            }else{
                if (network.checkNetwork()){
                    progressBarDialog.showProgressBar()
                    viewModel.getList()
                    listObserver()
                }
            }

        }

    }

    private fun listObserver(){
        viewModel.priceList.observe(viewLifecycleOwner){
            progressBarDialog.hideProgressBar()
            if (it.statusCode==200){
                viewModel.saveListToDb()
                setToAdapter(it.data)
            }else{
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setToAdapter(list: List<DataXX>){
        val adapter = PriceListAdapter(requireContext(),list)
        viewBinging.priceListRecv.adapter = adapter
    }


}