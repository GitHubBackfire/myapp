package com.example.backfire.myapp.bean;

/**
 * Created by backfire on 2018/6/11.
 */

public class EpubBook {
    private String bookPath;
    private String bookName;

    public EpubBook(String bookName, String bookPath) {
        this.bookPath = bookPath;
        this.bookName = bookName;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
