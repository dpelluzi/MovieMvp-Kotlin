package com.dpelluzi.moviedb.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dpelluzi.moviedb.R
import dpelluzi.moviedb.model.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private val data: MutableList<Movie> = mutableListOf()
    var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false)
        return MovieViewHolder(view)
    }

    fun addItems(items: List<Movie>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val titleView = view.findViewById<TextView>(R.id.text_title)

        private val overviewTextView = view.findViewById<TextView>(R.id.text_overview)

        private val ratingView = view.findViewById<TextView>(R.id.text_rating)

        init {
            view.setOnClickListener { onItemClickListener?.onItemClicked(data[adapterPosition]) }
        }

        fun bind(movie: Movie) {
            titleView.text = movie.title
            overviewTextView.text = movie.overview
            ratingView.text = ratingView.resources.getString(R.string.text_rating, movie.voteAverage)
        }
    }
}

interface OnItemClickListener {

    fun onItemClicked(movie: Movie)
}