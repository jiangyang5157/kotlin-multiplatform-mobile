package com.gmail.jiangyang5157.demo_router.fragmentroute

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.demo_router.Dependency
import com.gmail.jiangyang5157.demo_router.R
import com.gmail.jiangyang5157.demo_router.RouterFragmentActivityHost
import com.gmail.jiangyang5157.demo_router.databinding.ExampleActivityRouterBinding
import com.gmail.jiangyang5157.router.core.clear
import com.gmail.jiangyang5157.router.core.push
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

class ExampleActivity :
    AppCompatActivity(),
    RouterFragmentActivityHost<ExampleRoute> {

    @Suppress("UNCHECKED_CAST")
    override val router: FragmentRouter<ExampleRoute> =
        Dependency.fragmentrouteRouter["ExampleActivity"] as FragmentRouter<ExampleRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ExampleActivityRouterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (null == savedInstanceState) {
            router {
                clear() push ExampleRoute1("Push by ExampleActivity")
            }
        }
        router.setup(savedInstanceState, R.id.content_router)
    }

    override fun onBackPressed() {
        router.popRetainRootImmediateOrFinish()
    }
}
