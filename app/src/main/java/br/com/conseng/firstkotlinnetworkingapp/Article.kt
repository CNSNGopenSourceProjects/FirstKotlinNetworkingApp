package br.com.conseng.firstkotlinnetworkingapp

import org.json.JSONObject

/**
 * Created by Qin on 18/12/2017.
 */
class Article {
    lateinit var author: String
    lateinit var title: String
    lateinit var description: String
    lateinit var url: String
    lateinit var urlToImage: String
    lateinit var publishedAt: String

    private val jsonDataAuthor = "author"
    private val jsonDataTitle = "title"
    private val jsonDataDescription = "description"
    private val jsonDataUrl = "url"
    private val jsonDataUrlToImage = "urlToImage"
    private val jsonDataPublishedAt = "publishedAt"

    constructor(author: String, title: String, description: String,
    url: String, urlToImage: String, publishedAt: String) {
        this.author = author
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
    }
    constructor(json: JSONObject) {
        this.author = getJsonData(json, jsonDataAuthor)
        this.description = getJsonData(json, jsonDataDescription)
        this.url = getJsonData(json, jsonDataUrl)
        this.urlToImage = getJsonData(json, jsonDataUrlToImage)
        this.publishedAt = getJsonData(json, jsonDataPublishedAt)
    }

    private fun getJsonData(json: JSONObject, field: String): String {
        if (json.has(field))
            return json.getString(field)
        else
            return "null"
    }
}