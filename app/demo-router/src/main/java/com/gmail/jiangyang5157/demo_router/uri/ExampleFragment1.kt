package com.gmail.jiangyang5157.demo_router.uri

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.demo_router.RouterFragmentGuest
import com.gmail.jiangyang5157.demo_router.databinding.ExampleFragmentKeyrouteUriBinding
import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.popUntilPredicate
import com.gmail.jiangyang5157.router.core.push
import com.gmail.jiangyang5157.router.core.replaceTopWith
import com.gmail.jiangyang5157.router.core.route

class ExampleFragment1 :
    Fragment(),
    RouterFragmentGuest<UriRoute> {

    private val route: UriRoute by route()

    private var _binding: ExampleFragmentKeyrouteUriBinding? = null
    private val binding: ExampleFragmentKeyrouteUriBinding
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
        _binding = ExampleFragmentKeyrouteUriBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvInfo.text = "ExampleFragment1\n\n" +
            "# route=${route.data}\n\n" +
            "# param1=${route.query("param1")}\n"

        binding.btn1.setOnClickListener {
            router push UriRoute(
                "http://example.router.uri/page1?param1=Push by ExampleFragment1"
            )
        }

        binding.btn2.setOnClickListener {
            router push UriRoute(
                "http://example.router.uri/page2?param1=Push by ExampleFragment1"
            )
        }

        binding.btn3.setOnClickListener {
            router replaceTopWith UriRoute(
                "http://example.router.uri/page1?param1=replaceTopWith by ExampleFragment1"
            )
        }

        binding.btn4.setOnClickListener {
            router replaceTopWith UriRoute(
                "http://example.router.uri/page2?param1=replaceTopWith by ExampleFragment1"
            )
        }

        binding.btn5.setOnClickListener {
            router popUntilPredicate {
                it.route.key == Key("http://example.router.uri/page1")
            }
        }
    }
}
