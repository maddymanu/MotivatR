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

@ContentType("2wKn6yEnZewu2SCCkus4as")
@Parcel
public class Post extends Resource {

    @Field
    String title;

    @Field
    String slug;

//    @Field("author")
//    List<Author> authors;

    @Field
    String body;

  /*  @Field("category")
    List<Category> categories;*/

    @Field
    List<String> tags;

    @Field
    Asset featuredImage;

    @Field
    String date;

    @Field
    Boolean comments;

    public String title() {
        return title;
    }

    public String slug() {
        return slug;
    }

/*    public List<Author> authors() {
        return authors;
    }*/

    public String body() {
        return body;
    }

/*    public List<Category> categories() {
        return categories;
    }*/

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