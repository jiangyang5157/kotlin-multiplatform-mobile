package com.gmail.jiangyang5157.adapter.recycleview

import android.graphics.Canvas
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

interface ItemTouchHelperViewHolder {

    /**
     * Called when the [ItemTouchHelper] first registers an item as being moved or swiped.
     */
    fun onItemSelected()

    /**
     * Called when the [ItemTouchHelper] has completed the move or swipe on the item.
     */
    fun onItemClear()
}

/**
 * [onItemMove] Called when an item has been dragged far enough to trigger a move.
 *
 * This is called every time an item is shifted, and **not** at the end of a "drop" event.
 * Implementations consider to call [RecyclerView.Adapter.notifyItemMoved] after adjusting the underlying data to reflect this move.
 *
 * [onItemSwiped] Called when an item has been dismissed by a swipe.
 * Implementations consider to  call [RecyclerView.Adapter.notifyItemRemoved] after adjusting the underlying data to reflect this removal.
 */
class ItemTouchHelperCallback(
    private val onItemMove: ((fromPosition: Int, toPosition: Int) -> Unit)?,
    private val onItemSwiped: ((position: Int) -> Unit)?,
) : ItemTouchHelper.Callback() {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemSwiped?.invoke(viewHolder.adapterPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        onItemMove?.invoke(source.adapterPosition, target.adapterPosition) ?: return false
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        return if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlags = 0
            makeMovementFlags(dragFlags, swipeFlags)
        } else {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // View fading out during swiping
            viewHolder.itemView.alpha = 1.0f - abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(
                canvas,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchHelperViewHolder) {
                viewHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = 1.0f
        if (viewHolder is ItemTouchHelperViewHolder) {
            viewHolder.onItemClear()
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return onItemMove != null
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return onItemSwiped != null
    }
}
