package br.com.conseng.firstkotlinnetworkingapp

import android.app.LoaderManager
import android.content.AsyncTaskLoader
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        LoaderManager.LoaderCallbacks<List<Article>>,
        ArticleAdapter.ListItemClickListener {

    private val mArticleLoaderId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title=getString(R.string.news_source_title)

        rv_articles.layoutManager = LinearLayoutManager(this)
        rv_articles.setHasFixedSize(true)

        runLoaders()
    }

    private fun runLoaders() {
        progress_bar.visibility= View.VISIBLE

        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        if ((null != networkInfo) && (networkInfo.isConnected)) {
            loaderManager.initLoader(mArticleLoaderId, null, this)
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Article>> {
        val baseUri: Uri? = Uri.parse(getString(R.string.api_base_url))
        val uriBuilder: Uri.Builder? = baseUri?.buildUpon()
        uriBuilder?.appendQueryParameter(getString(R.string.query_param_source), getString(R.string.news_source))
        uriBuilder?.appendQueryParameter(getString(R.string.query_param_apikey), getString(R.string.news_api_org_key))
// BuildConfig.NewsApiOrgKey)
//        buildTypes.each {
//            it.buildConfigField('String', 'NewsApiOrgKey', '9dd2a151511143ab8365b9fd12fbd087')
//        }

        return object : AsyncTaskLoader<List<Article>>(this) {
            override fun onStartLoading() {
                forceLoad()
            }

            override fun loadInBackground(): List<Article>? {
                return QueryUtils.fetchArticleData(uriBuilder.toString())
            }
        }
    }

    override fun onLoadFinished(loader: Loader<List<Article>>?, articleList: List<Article>?) {
        progress_bar.visibility= View.INVISIBLE

        if ((null != articleList) && articleList.isNotEmpty()) {
            rv_articles.adapter = ArticleAdapter(this, articleList)
        }
    }

    override fun onLoaderReset(loader: Loader<List<Article>>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListItemClick(article: Article) {
        val articleUrl = Uri.parse(article.url)
        val browserIntent = Intent(Intent.ACTION_VIEW, articleUrl)
        startActivity(browserIntent)

    }
}
