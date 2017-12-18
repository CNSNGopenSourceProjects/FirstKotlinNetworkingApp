package br.com.conseng.firstkotlinnetworkingapp

import android.app.LoaderManager
import android.content.AsyncTaskLoader
import android.content.Context
import android.content.Loader
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Article>> {

    private val mArticleLoaderId = 1
    private val mNewsEndpointUrl = "https://newsapi.org/v2/top-headlines"
    private val mSource = "sources"
    private val mBBCnews = "bbc-news"
    private val mKey = "apiKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runLoaders()
    }

    private fun runLoaders() {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        if ((null != networkInfo) && (networkInfo.isConnected)) {
            loaderManager.initLoader(mArticleLoaderId, null, this)
        } else {
            Toast.makeText(thus, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return object : AsyncTaskLoader<List<Article>>(this) {
            override fun onStartLoading() {
                forceLoad()
            }

            override fun loadInBackground(): List<Article> {
                return QueryUtils.fetchArticleData()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<List<Article>>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(loader: Loader<List<Article>>?, data: List<Article>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
