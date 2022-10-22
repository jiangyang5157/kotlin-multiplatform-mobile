package com.gmail.jiangyang5157.common.util

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.google.android.material.tabs.TabLayout
import java.lang.ref.WeakReference

/**
 * Created by Yang Jiang on May 19, 2019
 */
class TabLayoutViewPager2Mediator(
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    private val onConfigureTabCallback: OnConfigureTabCallback,
    private val onSelectTabCallback: OnSelectTabCallback,
    private val mAutoRefresh: Boolean = true,
) {

    private var viewPagerAdapter: RecyclerView.Adapter<*>? = null
    private var onPageChangeCallback: TabLayoutOnPageChangeCallback? = null
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var pagerAdapterObserver: RecyclerView.AdapterDataObserver? = null
    private var attached: Boolean = false

    interface OnConfigureTabCallback {
        /**
         * To set the text and styling of newly created tabs.
         * @param position The adding position of [tab]
         */
        fun onConfigureTab(tab: TabLayout.Tab, position: Int)
    }

    interface OnSelectTabCallback {

        fun onTabSelected(tab: TabLayout.Tab)

        fun onTabUnselected(tab: TabLayout.Tab)

        fun onTabReselected(tab: TabLayout.Tab)
    }

    /**
     * Link the TabLayout and the ViewPager2 together.
     * @throws IllegalStateException If the mediator is already attached, or the ViewPager2 has no adapter.
     */
    fun attach() {
        if (attached) {
            throw IllegalStateException("TabLayoutMediator is already attached")
        }
        attached = true

        viewPagerAdapter = viewPager.adapter

        if (viewPagerAdapter == null) {
            throw IllegalStateException("TabLayoutMediator attached before ViewPager2 has an " + "adapter")
        }

        onPageChangeCallback = TabLayoutOnPageChangeCallback(
            tabLayout
        ).apply {
            viewPager.registerOnPageChangeCallback(this)
        }

        onTabSelectedListener =
            ViewPagerOnTabSelectedListener(
                viewPager,
                onSelectTabCallback
            ).apply {
                tabLayout.addOnTabSelectedListener(this)
            }

        if (mAutoRefresh) {
            pagerAdapterObserver = PagerAdapterObserver().apply {
                viewPagerAdapter?.registerAdapterDataObserver(this)
            }
        }

        populateTabsFromPagerAdapter()

        tabLayout.setScrollPosition(viewPager.currentItem, 0f, true)
    }

    /**
     * Unlink the TabLayout and the ViewPager
     */
    fun detach() {
        pagerAdapterObserver?.run {
            viewPagerAdapter?.unregisterAdapterDataObserver(this)
        }
        onTabSelectedListener?.run {
            tabLayout.removeOnTabSelectedListener(this)
        }
        onPageChangeCallback?.run {
            viewPager.unregisterOnPageChangeCallback(this)
        }
        pagerAdapterObserver = null
        onTabSelectedListener = null
        onPageChangeCallback = null
        attached = false
    }

    private fun populateTabsFromPagerAdapter() {
        tabLayout.removeAllTabs()

        if (viewPagerAdapter != null) {
            val adapterCount = viewPagerAdapter!!.itemCount
            for (i in 0 until adapterCount) {
                val tab = tabLayout.newTab()
                onConfigureTabCallback.onConfigureTab(tab, i)
                tabLayout.addTab(tab, false)
            }

            if (adapterCount > 0) {
                val currItem = viewPager.currentItem

                if (currItem != tabLayout.selectedTabPosition) {
                    tabLayout.getTabAt(currItem)!!.select()
                }
            }
        }
    }

    /**
     * A [ViewPager2.OnPageChangeCallback] class which contains the necessary calls back to the provided [TabLayout]
     * so that the tab position is kept in sync.
     */
    private class TabLayoutOnPageChangeCallback(tabLayout: TabLayout) :
        ViewPager2.OnPageChangeCallback() {
        private val tabLayoutRef: WeakReference<TabLayout> = WeakReference(tabLayout)
        private var prevScrollState: Int = 0
        private var scrollState: Int = 0

        override fun onPageScrollStateChanged(state: Int) {
            prevScrollState = scrollState
            scrollState = state
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val tabLayout = tabLayoutRef.get()

            if (tabLayout != null) {
                val updateText =
                    scrollState != SCROLL_STATE_SETTLING || prevScrollState == SCROLL_STATE_DRAGGING
                val updateIndicator =
                    !(scrollState == SCROLL_STATE_SETTLING && prevScrollState == SCROLL_STATE_IDLE)
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator)
            }
        }

        override fun onPageSelected(position: Int) {
            val tabLayout = tabLayoutRef.get()

            if (tabLayout != null &&
                tabLayout.selectedTabPosition != position &&
                position < tabLayout.tabCount
            ) {
                val updateIndicator =
                    scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_SETTLING && prevScrollState == SCROLL_STATE_IDLE
                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator)
            }
        }

        init {
            scrollState = SCROLL_STATE_IDLE
            prevScrollState = scrollState
        }
    }

    /**
     * A [TabLayout.OnTabSelectedListener] class which contains the necessary calls back to the provided [ViewPager2]
     * so that the tab position is kept in sync.
     */
    private class ViewPagerOnTabSelectedListener(
        private val viewPager: ViewPager2,
        private val selectTabCallback: OnSelectTabCallback
    ) : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.setCurrentItem(tab.position, true)
            selectTabCallback.onTabSelected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            selectTabCallback.onTabUnselected(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            selectTabCallback.onTabReselected(tab)
        }
    }

    private inner class PagerAdapterObserver :
        RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }
    }
}
