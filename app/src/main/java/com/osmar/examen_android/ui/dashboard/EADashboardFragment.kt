package com.osmar.examen_android.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.osmar.examen_android.R
import com.osmar.examen_android.databinding.FragmentEaDashboardBinding


class EADashboardFragment : Fragment() {

    private lateinit var binding: FragmentEaDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEaDashboardBinding.inflate(inflater, container, false)
        initElements()
        return binding.root
    }

    private fun initElements(){
        with(binding){
            cvPopular.setOnClickListener {
                findNavController().navigate(R.id.EAPopularMoviesFragment)
            }
            cvTopRated.setOnClickListener {
                findNavController().navigate(R.id.EATopRatedMoviesFragment)
            }
            cvUpComing.setOnClickListener {
                findNavController().navigate(R.id.EAUpComingMoviesFragment)
            }
            cvMap.setOnClickListener {
                findNavController().navigate(R.id.EALocationFragment)
            }

            cvImage.setOnClickListener {
                findNavController().navigate(R.id.EAUploadImageFragment)
            }
        }
    }


}