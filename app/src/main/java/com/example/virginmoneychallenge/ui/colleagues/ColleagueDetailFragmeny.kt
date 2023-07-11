package com.example.virginmoneychallenge.ui.colleagues

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.virginmoneychallenge.R
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.repository.Repository
import com.example.virginmoneychallenge.databinding.FragmentDetailColleagueBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColleagueDetailFragmeny : Fragment() {
    @Inject
    lateinit var repository: Repository
    lateinit var binding: FragmentDetailColleagueBinding
    private lateinit var colleague: Colleague

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailColleagueBinding.inflate(inflater, container, false)
        val currentId = arguments?.getString("id_character") ?: ""
        loadCharacter()


        return binding.root
    }

    private fun loadCharacter() {

            try {
               // setupProgressBar()
                //binding.circularProgressBar.visibility = VISIBLE
                colleague = Gson().fromJson(arguments?.getString("item"), Colleague::class.java)
                binding.apply {
                    tvFirstName.setText(getString(R.string.first_name, colleague.firstName))
                    tvLastName.setText(getString(R.string.last_name, colleague.lastName))
                    tvEmail.setText(getString(R.string.email_detail, colleague.email))
                    tvJobTitle.setText(getString(R.string.job_title, colleague.jobtitle))
                    tvFavouriteColor.setText(getString(R.string.favourite_color, colleague.favouriteColor))

                }
                Glide.with(requireContext())
                    .load(colleague.avatar)
                    .placeholder(R.drawable.avatar)
                    .into(binding.ivColleague)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                //binding.idProgressBar.visibility = View.GONE
            }

    }

    fun setupProgressBar(){
        val anim = ObjectAnimator.ofInt(binding.circularProgressBar, "progress", 0, 100)
        anim.duration = 15000
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }
}