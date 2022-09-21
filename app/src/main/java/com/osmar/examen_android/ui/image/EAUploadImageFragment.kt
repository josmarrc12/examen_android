package com.osmar.examen_android.ui.image

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.osmar.examen_android.databinding.FragmentEaUploadImageBinding

class EAUploadImageFragment : Fragment() {




    private lateinit var binding :FragmentEaUploadImageBinding
    private lateinit var EAUploadImageAdapter: EAUploadImageAdapter
    private var images:MutableList<String> = mutableListOf()
    private val PICK_IMAGE_REQUEST = 22
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentEaUploadImageBinding.inflate(inflater, container, false)

        binding.ivChoosePictures.setOnClickListener{
            selectImage()
        }
        EAUploadImageAdapter = EAUploadImageAdapter(images)
        binding.rvImages.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = EAUploadImageAdapter
        }
        return binding.root
    }




    private fun selectImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        intent.type = "*/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null){

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount){
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { uploadImages(it) }
                    }
                }
            }


        }
    }


    private fun uploadImages(mUri: Uri){

        val riversRef = storageRef.child("images/${mUri.lastPathSegment}")
        riversRef.putFile(mUri).addOnFailureListener{
            Toast.makeText(requireContext(), "Error al subir: ${it.toString()}", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(requireContext(), "Datos subidos: ${it.toString()}", Toast.LENGTH_SHORT).show()
        }

        riversRef.downloadUrl.addOnCompleteListener{
            if (it.isSuccessful){
                images.add(it.result.toString())
                Log.e("result","${it.result}")
            }
            EAUploadImageAdapter.setList(images)
        }





    }


}