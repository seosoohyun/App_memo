package com.ranggoo.app1_memo.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ranggoo.app1_memo.R
import com.ranggoo.app1_memo.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setFragmentResultListener는 onCreate에서 등록.
        // 왜냐하면 onCreate는 1번만 실행이 됨.
        // onCreate -> onCreateView -> onViewCreated -> onDestroyView(화면이사라짐) -> onDestroy(메모리에서 완전히삭제) 순서로 실행되는데,
        // onCreateView ~ onDestroyView 까지 프래그먼트가 이동할때마다 발생.(화면을 다시 그렸다가 지우는 코드임)
        // 만약에 onCreateView에 setFragmentResultListener가 있으면, 화면이 그려질때마다 리스너를 등록하기 때문에, 넘겨주는 bundle 값을 받을 수 없음
        // 따라서 항상 리슨을 하고 있어야 되기 때문에, onCreate에서 등록.
        setFragmentResultListener(requestKey = "ADD") { key, bundle ->
            val memoSubject = bundle.getString("memo_subject")?:""
            val memoContent = bundle.getString("memo_content")?:""
            viewModel.insertMemo(memoSubject,memoContent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_memoAddFragment)
        }

        rv.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_memoReadFragment)
        }
    }

    private fun initViewModel() {
        viewModel.memoList.observe(viewLifecycleOwner,{ memoList ->
            Log.d("Test","메모리스트: $memoList")

        })
    }

    //앱이 화면에서 삭제 시 메모리에서 삭제가 실행되는 코드!!!!!
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}