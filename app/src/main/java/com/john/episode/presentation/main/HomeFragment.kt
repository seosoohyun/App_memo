package com.john.episode.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.john.episode.databinding.FragmentHomeBinding
import com.john.episode.domain.feed.entity.EpisodeFeedContentEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val homeAdapter = HomeAdapter(::onClickEpisode)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(requestKey = "ADD") { key, bundle ->
            val memoSubject = bundle.getString("memo_subject") ?: ""
            val memoContent = bundle.getString("memo_content") ?: ""

        }
    }

    private fun onClickEpisode(home: EpisodeFeedContentEntity) {


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        // recyclerview init.
        with(rv) {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    private fun initViewModel() {
        viewModel.feedList
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { (feedList) ->
                Timber.tag("FeedList").d("$feedList")
               homeAdapter.submitList(feedList)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //앱이 화면에서 삭제 시 메모리에서 삭제가 실행되는 코드!!!!!
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}