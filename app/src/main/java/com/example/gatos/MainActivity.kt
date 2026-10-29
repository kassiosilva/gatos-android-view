package com.example.gatos

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gatos.databinding.ActivityMainBinding
import com.example.gatos.network.model.Cat
import com.example.gatos.ui.CatsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<CatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getCats()

        with(binding) {
            lMainLcs.srlLcs.apply {
                setOnRefreshListener {
                    viewModel.getCats()
                }

                setColorSchemeResources(
                    R.color.button
                )
            }

            lMainLce.btnLceRetry.setOnClickListener {
                showContentLoading()
                viewModel.getCats()
            }

            lifecycleScope.launch {
                viewModel.cats.collectLatest { result ->
                    result.onSuccess { cats ->
                        if (cats.isNotEmpty()) {
                            showContentSuccess()
                            lMainLcs.rvLcsCats.adapter = CatsAdapter(cats)
                        }
                    }.onFailure {
                        showContentError()
                    }

                    lMainLcs.srlLcs.isRefreshing = false
                }
            }
        }
    }

    private fun ActivityMainBinding.showContentLoading() {
        pbMainLoading.visibility = View.VISIBLE
        lMainLcs.root.visibility = View.GONE
        lMainLce.root.visibility = View.GONE
    }

    private fun ActivityMainBinding.showContentSuccess() {
        pbMainLoading.visibility = View.GONE
        lMainLcs.root.visibility = View.VISIBLE
        lMainLce.root.visibility = View.GONE
    }

    private fun ActivityMainBinding.showContentError() {
        pbMainLoading.visibility = View.GONE
        lMainLcs.root.visibility = View.GONE
        lMainLce.root.visibility = View.VISIBLE
    }

}