package com.example.imagegallery.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagegallery.R
import com.example.imagegallery.databinding.ActivityMainBinding
import com.example.imagegallery.ui.adapter.ImageAdapter
import com.example.imagegallery.ui.adapter.LoaderStateAdapter
import com.example.imagegallery.ui.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mainViewModel:ImageViewModel by viewModels()

    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageAdapter = ImageAdapter(this)
        initRecyclerview()
        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllImages.collectLatest { response->
                binding.apply {
                    progressBar.visibility = View.GONE
                    recyclerview.visibility = View.VISIBLE
                }
                imageAdapter.submitData(response)
            }
        }
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity,2)
                adapter = imageAdapter/*.withLoadStateFooter(
//                    header = LoaderStateAdapter { imageAdapter :: retry},
                    footer = LoaderStateAdapter{imageAdapter :: retry}
                )*/

            }
//            lifecycleScope.launch {
//                imageAdapter.loadStateFlow.collectLatest { loadStates ->
//
////                        retryBtn.isVisible = loadStates.refresh !is LoadState.Loading
//                }
//            }
            imageAdapter.addLoadStateListener { loadStates->
                if (loadStates.source.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached && imageAdapter.itemCount < 1) {
                    progressBar.isVisible = true
                }
            }
        }
    }
}