package com.gmail.jiangyang5157.demo_router.fragmentroute

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.demo_router.RouterFragmentGuest
import com.gmail.jiangyang5157.demo_router.databinding.ExampleFragmentFragmentrouteBinding
import com.gmail.jiangyang5157.router.core.push
import com.gmail.jiangyang5157.router.core.replaceTopWith
import com.gmail.jiangyang5157.router.core.route

class ExampleFragment2 :
    Fragment(),
    RouterFragmentGuest<ExampleRoute> {

    private val route: ExampleRoute by route()

    private var _binding: ExampleFragmentFragmentrouteBinding? = null
    private val binding: ExampleFragmentFragmentrouteBinding
        get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExampleFragmentFragmentrouteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvInfo.text = "ExampleFragment2\n\n" +
                "# route=${route.data}\n"

        binding.btn1.setOnClickListener {
            router push ExampleRoute1(
                "Push by ExampleFragment2"
            )
        }

        binding.btn2.setOnClickListener {
            router push ExampleRoute2(
                "Push by ExampleFragment2"
            )
        }

        binding.btn3.setOnClickListener {
            router replaceTopWith ExampleRoute1(
                "replaceTopWith ExampleRoute1 by ExampleFragment2"
            )
        }

        binding.btn4.setOnClickListener {
            router replaceTopWith ExampleRoute2(
                "replaceTopWith ExampleRoute2 by ExampleFragment2"
            )
        }
    }
}
