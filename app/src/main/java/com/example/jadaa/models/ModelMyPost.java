package com.example.jadaa.models;

public class ModelMyPost {

    String BookAuthor;
    String BookDescription;
    String BookImage;
    String BookPrice;
    String BookStatus;
    String BookTitle;
    String College;
    String PostDate;
    String PostTime;
    String Publisher;
    String uid;
    String BookEdition;
    String pId;


    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }



    public ModelMyPost(String BookTitle, String BookDescription, String BookImage, String BookStatus, String BookPrice, String uid, String college, String bookAuthor, String bookEdition, String postDate, String postTime, String publisher, String pId) {
        this.BookTitle = BookTitle;
        this.BookDescription = BookDescription;
        this.BookImage = BookImage;
        this.BookStatus = BookStatus;
        this.BookPrice = BookPrice;
        this.uid = uid;
        this.College = college;
        this.BookAuthor = bookAuthor;
        this.BookEdition = bookEdition;
        this.PostDate = postDate;
        this.PostTime = postTime;
        this.Publisher = publisher;
        this.pId = pId;
    }



    public ModelMyPost() {

    }



    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public void setBookDescription(String bookDescription) {
        BookDescription = bookDescription;
    }

    public void setBookImage(String bookImage) {
        BookImage = bookImage;
    }

    public void setBookStatus(String bookStatus) {
        BookStatus = bookStatus;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCollege(String college) {
        College = college;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public void setBookEdition(String bookEdition) {
        BookAuthor = bookEdition;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public String getBookDescription() {
        return BookDescription;
    }

    public String getBookImage() {
        return BookImage;
    }

    public String getBookStatus() {
        return BookStatus;
    }

    public String getBookPrice() {
        return BookPrice;
    }

    public String getUid() {
        return uid;
    }

    public String getCollege() {
        return College;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public String getBookEdition() {
        return BookEdition;
    }

    public String getPostDate() {
        return PostDate;
    }

    public String getPostTime() {
        return PostTime;
    }

    public String getPublisher() {
        return Publisher;
    }

}
