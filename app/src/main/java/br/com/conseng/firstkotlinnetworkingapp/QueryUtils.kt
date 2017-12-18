package br.com.conseng.firstkotlinnetworkingapp

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.math.log

object QueryUtils {

    private val logTag = QueryUtils::class.java.simpleName

    fun fetchArticleData(requestUrl: String): List<Article>? {
        val url = createUrl(requestUrl)

        var jsonResponse:String? = null
        try {
           jsonResponse = getResponseFromHttpUrl(url)
        } catch (e:IOException){
            Log.e(logTag, "Problem making the HTTP request", e)
        }
        return extractFetureFromJson(jsonResponse)
    }

    private fun createUrl(requestUrl: String): URL? {
        var url: URL? = null
        try {
            url = URL(requestUrl)
        } catch (e: MalformedURLException) {
            Log.e(logTag, "Problem building the URL", e)
        }
        return url
    }

    @Throws(IOException::class)
    private fun getResponseFromHttpUrl(url: URL?): String? {
        val urlConnection = url?.openConnection() as HttpURLConnection?
        try {
            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK){
                val inputStream = urlConnection?.inputStream
                val scanner = Scanner(inputStream)
                scanner.useDelimiter("\\A")
                if (scanner.hasNext()) {
                    return  scanner.next()
                }
            } else {
                Log.e(logTag, "Error response code: " + urlConnection?.responseCode)
            }
        } finally {
            urlConnection?.disconnect()
        }

        return null
    }

    private fun extractFetureFromJson(jsonResponse: String?): List<Article>? {
        if (jsonResponse.isNullOrEmpty()) {
            return null
        }
        val articles = mutableListOf<Article>()
        try {
            val baseJsonResponse = JSONObject(jsonResponse)
            val articleArray = baseJsonResponse.getJSONArray("articles")

            for (i in 0..articleArray.length()-1) {
                val currentArticle = Article(articleArray.getJSONObject(i))
                articles.add(currentArticle)
            }
        } catch (e: JSONException) {
            Log.e(logTag, "Problem parsin the article JSON results", e)
        }
        return articles
    }
}
