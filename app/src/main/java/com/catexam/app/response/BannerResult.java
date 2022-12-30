package com.catexam.app.response;

public class BannerResult {
    int ImageUrl;

    public BannerResult(int imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(int imageUrl) {
        ImageUrl = imageUrl;
    }
}