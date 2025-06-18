package com.example.moviesapp.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.example.moviesapp.domain.model.Movie
import com.google.firebase.FirebaseApp
import com.google.firebase.dynamiclinks.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.shortLinkAsync
import com.google.firebase.dynamiclinks.socialMetaTagParameters
import com.google.firebase.ktx.Firebase


/**
 *created by Abhijeet Sharma on 18-06-2025
 */
object Utils {

    fun shareMovie(context: Context, movie: Movie?) {
        Firebase.dynamicLinks.shortLinkAsync {
            link = "https://movieapp.page.link/movie?id=${movie?.id}".toUri()
            domainUriPrefix = "https://movieapp.page.link"
            androidParameters {}
            socialMetaTagParameters {
                title = movie!!.title
                description = movie.overview
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.posterPath}".toUri()
            }
        }.addOnSuccessListener { result ->
            val shortLink = result.shortLink
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Check out this movie!")
                putExtra(Intent.EXTRA_TEXT, "Watch ${movie!!.title} \n$shortLink")
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }.addOnFailureListener {it->
            Toast.makeText(context, "Failed to generate share link", Toast.LENGTH_SHORT).show()
        }
    }

}