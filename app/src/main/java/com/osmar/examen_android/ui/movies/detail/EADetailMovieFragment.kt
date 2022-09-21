package com.osmar.examen_android.ui.movies.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.osmar.examen_android.R
import com.osmar.examen_android.data.EAApiConstants
import com.osmar.examen_android.databinding.FragmentEaDetailMovieBinding
import com.osmar.examen_android.ui.movies.EAMoviesViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EADetailMovieFragment : Fragment() {

    private lateinit var binding: FragmentEaDetailMovieBinding
    private val viewModel:EADetailMovieViewModel by viewModels()
    private val args: EADetailMovieFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
       binding = FragmentEaDetailMovieBinding.inflate(inflater,container,false)
        viewModel.getLocalMovie(args.movieId)
        initStateUI()
        return binding.root
    }

    private fun initElements(){
        val movie = viewModel.movie.value
        with(binding){
            tvTitle.text = movie?.title
            tvOverview.text = movie?.overview
            tvDate.text = movie?.releaseDate

            Glide.with(ivImage.context)
                .load(EAApiConstants.SERVER_PATH_IMAGE+movie?.backdropPath)
                .circleCrop()
                .into(ivImage)
        }
    }

    private fun initStateUI() {
        viewLifecycleOwner.lifecycleScope.launch{
            launch {
                viewModel.movie.collectLatest {
                    initElements()
                }
            }
        }
    }




}