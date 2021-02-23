package com.klewerro.radommap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klewerro.radommap.data.InterestPoint
import com.klewerro.radommap.databinding.RecyclerItemBinding

class DestinationsRecyclerAdapter(private val listener: OnDestinationClickListener) : RecyclerView.Adapter<DestinationsRecyclerAdapter.DestinationViewHolder>() {

    private var interestPoints = ArrayList<InterestPoint>()

    fun setList(items: List<InterestPoint>) {
        interestPoints = ArrayList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val currentInterestPoint = interestPoints[position]
        holder.bind(currentInterestPoint)
    }

    override fun getItemCount() = interestPoints.size



    inner class DestinationViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val interestPoint = interestPoints[adapterPosition]
                    listener.onItemClick(interestPoint)
                }
            }
        }

        fun bind(interestPoint: InterestPoint) {
            binding.apply {
                nameEditText.text = interestPoint.name
                descriptionEditText.text = interestPoint.description
            }
        }
    }

    interface OnDestinationClickListener {
        fun onItemClick(interestPoint: InterestPoint)
    }
}