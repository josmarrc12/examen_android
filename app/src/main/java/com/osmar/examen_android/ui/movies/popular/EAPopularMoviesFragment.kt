package com.osmar.examen_android.ui.movies.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.osmar.examen_android.R
import com.osmar.examen_android.data.models.EAMovie
import com.osmar.examen_android.databinding.FragmentEaPopularMoviesBinding
import com.osmar.examen_android.ui.movies.EAMoviesAdapter
import com.osmar.examen_android.ui.movies.EAMoviesViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EAPopularMoviesFragment : Fragment() {

    private lateinit var binding: FragmentEaPopularMoviesBinding
    private val viewModel:EAPopularMoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentEaPopularMoviesBinding.inflate(inflater,container,false)
        initElements()
        initStateUI()
        return binding.root
    }

    private fun initStateUI() {
        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->

            when(state){
                is EAMoviesViewState.Success -> {
                    initRecyclerView(state.data)
                }
                is EAMoviesViewState.Error -> {
                    Toast.makeText(requireContext(),getString(R.string.ea_error_request),Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initElements(){
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.getPopularMovies()
            }
        }

    }

    private fun initRecyclerView(moviesList:List<EAMovie>?){
        with(binding.rvImages){
            layoutManager = LinearLayoutManager(requireContext())
            moviesList?.let {
                adapter = EAMoviesAdapter(moviesList){ movie ->
                        val action = EAPopularMoviesFragmentDirections.actionEAPopularMoviesFragmentToEADetailMovieFragment(movie.id)
                        findNavController().navigate(action)
                }
            }
        }
    }



}