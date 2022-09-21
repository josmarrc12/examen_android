package com.osmar.examen_android.ui.image

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osmar.examen_android.databinding.ItemEaImagesBinding

class EAUploadImageAdapter(
    var images: List<String>
): RecyclerView.Adapter<EAUploadImageAdapter.UploadImageViewHolder>(){

    class UploadImageViewHolder(
        private val context: Context,
        private val binding: ItemEaImagesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url : String){
                Glide
                    .with(context)
                    .load(url)
                    .into(binding.ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vBind = ItemEaImagesBinding.inflate(inflater, parent, false)
        return UploadImageViewHolder(parent.context, vBind)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
       return images.size
    }

    fun setList(images: List<String>){
        this.images = images

        notifyDataSetChanged()
    }


}