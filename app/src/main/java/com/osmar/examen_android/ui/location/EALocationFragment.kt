package com.osmar.examen_android.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.osmar.examen_android.R
import com.osmar.examen_android.data.FirebaseResult
import com.osmar.examen_android.data.models.EALocationPoint
import com.osmar.examen_android.databinding.FragmentEaLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EALocationFragment : Fragment(), OnMapReadyCallback {

  lateinit var binding: FragmentEaLocationBinding
  private val viewModel: EALocationViewModel by viewModels()
    private lateinit var gMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
       binding = FragmentEaLocationBinding.inflate(inflater,container,false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        initUpdates()
    }


    private fun initUpdates(){
        viewModel.getLocations()

        viewModel.locations.observe(viewLifecycleOwner){
            binding.pbLoading.visibility = if(it is FirebaseResult.Loading) View.VISIBLE else View.GONE

            if(it is FirebaseResult.Error){
                Toast.makeText(context, R.string.ea_error_request, Toast.LENGTH_SHORT).show()
            }

            if(it is FirebaseResult.Success){
                showMarkers(it.data?: listOf())
            }
        }
    }

    private fun showMarkers(points:List<EALocationPoint>){
        var position: LatLng? = null
        gMap.clear()
        points.forEach {
            val position = LatLng(
                it.latitude?:0.0,
                it.longitude?:0.0
            )
            gMap.addMarker(
                MarkerOptions()
                    .position(position)
            )
        }
        points.lastOrNull()?.let {
            val cameraPosition = CameraPosition.builder()
                .target(
                    LatLng(
                        it.latitude?:0.0,
                        it.longitude?:0.0
                    )
                )
                .zoom(15f)
                .build()
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }


}