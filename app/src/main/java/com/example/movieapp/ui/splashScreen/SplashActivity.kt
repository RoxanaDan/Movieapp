package com.example.movieapp.ui.splashScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.ui.OnBoardingActivity
import com.example.movieapp.R
import com.example.movieapp.SearchActivity
import com.example.movieapp.ui.actors.ActorRepository
import com.example.movieapp.ui.genres.GenreRepository
import com.example.movieapp.ui.movies.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

private const val DELAY = 3000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private val genreRepository = GenreRepository.instance
    private val actorRepository = ActorRepository.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splahscreen)
        initHandlerToOpenNextActivity()
        val imageView = findViewById<ImageView>(R.id.ivLogo)

        imageView.twinkle()
    }

    private fun initHandlerToOpenNextActivity() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            openNextScreen()
        }

        handler?.postDelayed(runnable!!, DELAY)
    }

    private fun openNextScreen() {
        isSaved()
        finish()
    }

    private fun isSaved(){
        GlobalScope.launch(Dispatchers.IO) {
            val genreCount = genreRepository.getCount()
            val actorCount = actorRepository.getCount()
            withContext(Dispatchers.Main) {
                // operatiuni
            verifyIsSaved(genreCount, actorCount)
            }
        }
    }

    private fun verifyIsSaved(genreCount: Int, actorCount: Int) {
        val isSaved = genreCount > 0  && actorCount > 0
        if(isSaved)
        SearchActivity.open(this)
        else
        OnBoardingActivity.open(this)
    }

    override fun onDestroy() {
        removeHandler()
        super.onDestroy()
    }

    override fun onBackPressed() {
        removeHandler()
        super.onBackPressed()
    }

    private fun removeHandler() {
        if (handler != null && runnable != null) {
            handler?.removeCallbacks(runnable!!)
            runnable = null
            handler = null
        }
    }

    private fun View.twinkle(
        drawableRes: Int = R.drawable.logo,
        duration: Int = 600,
        sparsity: Int = 120,
        size: Int = 80
    ): Twinkle {
        return Twinkle(this, drawableRes, duration, sparsity, size)
    }

    /**
     * Main Twinkle class
     */
    class Twinkle(
        var view: View,
        var drawableRes: Int,
        var duration: Int,
        private var sparsity: Int,
        var size: Int
    ) {
        private var isRunning = true

        init {
            start()
        }

        fun start() {
            isRunning = true
            val rInt = Random()
            loop(rInt.nextInt(sparsity * 2) + (sparsity / 2L)) {
                //creating the ImageView. Not the most optimal way of showing graphics, but it's easy
                var image: ImageView? = ImageView(view.context)
                image?.apply {
                    layoutParams = ViewGroup.LayoutParams(size, size)
                    setBackgroundResource(drawableRes)
                    try {
                        x =
                            (rInt.nextInt((view.width / 1.1).toInt()) + view.x + view.width * 0.1f) - (layoutParams.width / 2)
                        y =
                            (rInt.nextInt((view.height / 1.1).toInt()) + view.y + view.height * 0.1f) - (layoutParams.height / 2)
                    } catch (e: Exception) {
                        return@apply
                    }
                    (view.context as? Activity)?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
                        ?.addView(this)
                    alpha = 0f
                    scaleX = 0.7f
                    scaleY = 0.7f
                    animate().alpha(0.5f).duration = duration / 3L
                    animate().scaleX(1f).duration = duration / 3L
                    animate().scaleY(1f).duration = duration / 3L
                    loop(150) {
                        //                    alpha = 0.5f
                        animate().alpha(0.5f).duration = 70
                        delay(80) {
                            animate().alpha(0.4f).duration = 30
//                        alpha = 0.4f
                        }
                    }
                    //animation
                    delay(duration / 3L) {
                        animate().rotation(-10f).duration = duration / 6L
                        delay(duration / 6L) {
                            animate().rotation(10f).duration = duration / 6L * 2
                            delay(duration / 6L * 2) {
                                animate().rotation(0f).duration = duration / 6L
                            }
                        }
                        delay(duration / 3L) {
                            animate().alpha(0f).duration = duration / 3L
                            animate().scaleX(0.7f).duration = duration / 3L
                            animate().scaleY(0.7f).duration = duration / 3L
                        }
                    }
                }
                delay(duration.toLong()) {
                    (view.context as? Activity)?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
                        ?.removeView(image)
                    image = null
                }
            }
        }


        fun stop() {
            isRunning = false
        }

        private fun loop(delay: Long, f: () -> Unit) {
            f()
            var handler: Handler? = Handler()
            val runnable = object : Runnable {
                override fun run() {
                    if (!isRunning) {
                        handler = null
                        return
                    }
                    try {
                        f()
                    } catch (e: Exception) {
                        Log.d("Twinkle error", e.localizedMessage)
                    }; handler?.postDelayed(this, delay)
                }
            }; handler?.postDelayed(runnable, delay)
        }

        private fun delay(delay: Long, func: () -> Unit) {
            val handler = Handler()
            handler.postDelayed({
                try {
                    func()
                } catch (e: Exception) {
                    Log.d("Twinkle error", e.localizedMessage)
                }
            }, delay)
        }

    }
}