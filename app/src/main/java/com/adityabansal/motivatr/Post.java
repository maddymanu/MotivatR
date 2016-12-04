package com.adityabansal.motivatr;

/**
 * Created by adityabansal on 11/4/16.
 */

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;

import org.parceler.Parcel;

import java.util.List;

/**
 * POST - Container class for CMS data type. Easily modifiable.
 */
@ContentType("2wKn6yEnZewu2SCCkus4as")
@Parcel
public class Post extends Resource {

    //Public fields for Parcelable format.
    @Field
    public String title;

    @Field
    public String slug;


    @Field
    public String body;


    @Field
    public List<String> tags;

    @Field
    public Asset featuredImage;

    @Field
    public String date;

    @Field
    public Boolean comments;

    public String title() {
        return title;
    }

    public String slug() {
        return slug;
    }

    public String body() {
        return body;
    }

    public List<String> tags() {
        return tags;
    }

    public Asset featuredImage() {
        return featuredImage;
    }

    public String date() {
        return date;
    }

    public boolean isComments() {
        return comments;
    }
}