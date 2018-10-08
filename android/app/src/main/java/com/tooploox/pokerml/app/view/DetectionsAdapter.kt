package com.tooploox.pokerml.app.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.tooploox.pokerml.domain.entity.Detection

class DetectionsAdapter() : RecyclerView.Adapter<DetectionsAdapter.ViewHolder>() {

    var detections: List<Detection>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PlayingCardView(parent.context))
    }

    override fun getItemCount(): Int = detections?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.displayDetection(detections!![position])
    }

    class ViewHolder(val view: PlayingCardView) : RecyclerView.ViewHolder(view)

}
