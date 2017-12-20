package br.com.conseng.firstkotlinnetworkingapp

import org.json.JSONObject

class Article {
    val author: String
    val title: String
    val description: String
    val url: String
    val urlToImage: String
    val publishedAt: String

    private val fieldnameEmptyValue = "null"

    constructor(author: String, title: String, description: String,
                url: String, urlToImage: String, publishedAt: String) {
        this.author = getStringWithDefault(author)
        this.title = getStringWithDefault(title)
        this.description = getStringWithDefault(description)
        this.url = getStringWithDefault(url)
        this.urlToImage = getStringWithDefault(urlToImage)
        this.publishedAt = getStringWithDefault(publishedAt)
    }

    private fun getStringWithDefault(fieldValue: String): String {
        return if (fieldValue.isEmpty()) fieldnameEmptyValue else fieldValue
    }

    private val jsonFieldnameAuthor = "author"
    private val jsonFieldnameTitle = "title"
    private val jsonFieldnameDescription = "description"
    private val jsonFieldnameDataUrl = "url"
    private val jsonFieldnameUrlToImage = "urlToImage"
    private val jsonFieldnamePublishedAt = "publishedAt"

    constructor(json: JSONObject) {
        this.author = getJsonData(json, jsonFieldnameAuthor)
        this.title = getJsonData(json, jsonFieldnameTitle)
        this.description = getJsonData(json, jsonFieldnameDescription)
        this.url = getJsonData(json, jsonFieldnameDataUrl)
        this.urlToImage = getJsonData(json, jsonFieldnameUrlToImage)
        this.publishedAt = getJsonData(json, jsonFieldnamePublishedAt)
    }

    private fun getJsonData(json: JSONObject, fieldname: String): String {
        return if (json.has(fieldname)) json.getString(fieldname) else fieldnameEmptyValue
    }
}