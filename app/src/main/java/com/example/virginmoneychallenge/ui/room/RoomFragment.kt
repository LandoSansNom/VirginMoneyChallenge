package com.example.virginmoneychallenge.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virginmoneychallenge.data.repository.Repository
import com.example.virginmoneychallenge.databinding.FragmentRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomFragment : Fragment() {

    @Inject
    lateinit var repository: Repository
    val viewmodel: RoomViewModel by viewModels()
    private lateinit var binding: FragmentRoomBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentRoomBinding.inflate(inflater, container, false)


        if (!viewmodel.isLoaded) {
            viewmodel.getAllRooms()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            viewmodel.models.observe(viewLifecycleOwner) {

                binding.rvRoom.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = RoomAdapter(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }


    }
}