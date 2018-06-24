package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

/**
 * Created by goandroid on 6/23/18.
 */

public class Review {
//    "results":[
//       {
//               "author":"belushi123",
//               "content":"The Second World .....d.\r\n\r\nReviewed by Zoë Rose Smith",
//               "id":"55987013c3a3684baa00097b",
//               "url":"https://www.themoviedb.org/review/55987013c3a3684baa00097b"
//       }
//    ],

    private String author;
    private String content;
    private String id;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
