package com.example.virginmoneychallenge.ui.colleagues

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virginmoneychallenge.data.repository.Repository
import com.example.virginmoneychallenge.databinding.FragmentColleagueBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ColleagueFragment : Fragment() {

    @Inject
    lateinit var repository: Repository

    val viewmodel: ColleagueViewModel by viewModels()

    private lateinit var binding: FragmentColleagueBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentColleagueBinding.inflate(inflater, container, false)


        if (!viewmodel.isLoaded) {
            viewmodel.getAllColleagues()
        }

        viewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
          //  setupProgressBar()
            binding.circularProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            viewmodel.models.observe(viewLifecycleOwner) {

                binding.rvColleague.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ColleagueAdapter(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }


    }

    fun setupProgressBar(){
        val anim = ObjectAnimator.ofInt(binding.circularProgressBar, "progress", 0, 100)
        anim.duration = 15000
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }

}

