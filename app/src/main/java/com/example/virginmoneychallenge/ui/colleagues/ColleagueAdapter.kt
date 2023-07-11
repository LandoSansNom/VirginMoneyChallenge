package com.example.virginmoneychallenge.ui.colleagues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.virginmoneychallenge.R
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.databinding.ItemColleagueBinding
import com.google.gson.Gson

class ColleagueAdapter(val colleagues: List<Colleague>): RecyclerView.Adapter<ColleagueAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {


        val binding = ItemColleagueBinding.bind(view)
        fun updateUI(colleague: Colleague) {
            binding.apply {
                tvFirstName.text = colleague.firstName
                tvLastName.text = colleague.lastName
                tvEmail.text = colleague.email

                Glide.with(view)
                    .load(colleague.avatar)
                    .placeholder(R.drawable.avatar)

                    .into(ivAvatar)

                cvCharacter.setOnClickListener {
                    val navController = Navigation.findNavController(view)
                    navController.navigate(R.id.action_navigation_home_to_navigation_detail_colleague,
                        bundleOf(
                            "item" to Gson().toJson(colleague)
                        )
                    )

                }
            }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_colleague,parent,false)

        )
    }

    override fun getItemCount(): Int {
        return colleagues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        colleagues[position]?.let { holder.updateUI(it) }

    }


}