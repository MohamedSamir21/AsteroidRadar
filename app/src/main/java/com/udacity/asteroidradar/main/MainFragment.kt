package com.udacity.asteroidradar.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener {
            viewModel.displayAsteroidDetails(it)
        })

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_asteroids-> viewModel.viewWeekAsteroids()
            R.id.show_saved_asteroids -> viewModel.viewSavedAsteroids()
            R.id.show_today_asteroids-> viewModel.viewTodayAsteroid()
        }
        return true
    }


    init {
        instance = this
    }

    companion object {
        private var instance: MainFragment? = null

        fun applicationContext() : Context {
            return instance!!.requireContext()
        }
    }
}
