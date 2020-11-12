package com.example.jadaa.models;

public class soldBooks {

    String BookPrice;
    String BookTitle;
    String delivered;
    String inTransit;
    String pId;
    String processing;
    String purchaseDate;
    String purchaseTime;
    String purchaserID;
    String sellerID;
    String shipped;
    String orderConfirmation;
    String uri;
    String BookEdition;
    String purchaserName;

    public soldBooks(String bookPrice, String bookTitle, String delivered, String inTransit, String pId, String processing, String purchaseDate, String purchaseTime, String purchaserID, String sellerID, String shipped, String orderConfirmation, String uri, String bookEdition, String purchaserName, String purchaserPhone, String purchaserEmail, String bookAuthor) {
        BookPrice = bookPrice;
        BookTitle = bookTitle;
        this.delivered = delivered;
        this.inTransit = inTransit;
        this.pId = pId;
        this.processing = processing;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.purchaserID = purchaserID;
        this.sellerID = sellerID;
        this.shipped = shipped;
        this.orderConfirmation = orderConfirmation;
        this.uri = uri;
        BookEdition = bookEdition;
        this.purchaserName = purchaserName;
        this.purchaserPhone = purchaserPhone;
        this.purchaserEmail = purchaserEmail;
        BookAuthor = bookAuthor;
    }

    String purchaserPhone;
    String purchaserEmail;


    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserPhone() {
        return purchaserPhone;
    }

    public void setPurchaserPhone(String purchaserPhone) {
        this.purchaserPhone = purchaserPhone;
    }

    public String getPurchaserEmail() {
        return purchaserEmail;
    }

    public void setPurchaserEmail(String purchaserEmail) {
        this.purchaserEmail = purchaserEmail;
    }







    public String getBookEdition() {
        return BookEdition;
    }

    public void setBookEdition(String bookEdition) {
        BookEdition = bookEdition;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    String BookAuthor;


    public String getOrderConfirmation() {
        return orderConfirmation;
    }

    public void setOrderConfirmation(String orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public soldBooks(){}



    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getInTransit() {
        return inTransit;
    }

    public void setInTransit(String inTransit) {
        this.inTransit = inTransit;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }








}
