package com.gmail.jiangyang5157.demo_router.keyroute.uri

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.demo_router.Dependency
import com.gmail.jiangyang5157.demo_router.R
import com.gmail.jiangyang5157.demo_router.RouterFragmentActivityHost
import com.gmail.jiangyang5157.demo_router.databinding.ExampleActivityRouterBinding
import com.gmail.jiangyang5157.router.core.clear
import com.gmail.jiangyang5157.router.core.push
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

class ExampleCustomRouteStorageActivity :
    AppCompatActivity(),
    RouterFragmentActivityHost<UriRoute> {

    @Suppress("UNCHECKED_CAST")
    override val router: FragmentRouter<UriRoute> =
        Dependency.uriRouter["ExampleCustomRouteStorageActivity"] as FragmentRouter<UriRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ExampleActivityRouterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (null == savedInstanceState) {
            router {
                clear() push UriRoute("http://example.router.uri/page1?param1=Push by ExampleCustomRouteStorageActivity")
            }
        }
        router.setup(savedInstanceState, R.id.content_router)
    }

    override fun onBackPressed() {
        router.popRetainRootImmediateOrFinish()
    }
}
