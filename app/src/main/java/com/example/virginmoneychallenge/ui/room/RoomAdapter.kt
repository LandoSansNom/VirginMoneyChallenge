package com.example.virginmoneychallenge.ui.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virginmoneychallenge.R
import com.example.virginmoneychallenge.data.model.Room
import com.example.virginmoneychallenge.databinding.ItemRoomBinding

class RoomAdapter(val rooms: List<Room>): RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {


        val binding = ItemRoomBinding.bind(view)
        fun updateUI(room: Room) {
            binding.apply {

                    tvCreatedDate.text = view.context.getString(R.string.created_date, room.createdAt)
                tvOccupancy.text = view.context.getString(R.string.occupancy, room.maxOccupancy.toString())

                if (room.isOccupied == true){
                    tvIsOccupy.text = view.context.getString(R.string.is_occupied, "yes")
                    ivAvailibility.setImageResource(R.drawable.redcross)
                }else{
                    tvIsOccupy.text = view.context.getString(R.string.is_occupied, "no")
                    ivAvailibility.setImageResource(R.drawable.greencheck)

                }
            }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room,parent,false)

        )
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        rooms[position]?.let { holder.updateUI(it) }

    }


}