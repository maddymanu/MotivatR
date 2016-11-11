package com.adityabansal.motivatr;

import java.util.LinkedHashSet;
import java.util.Set;

import io.paperdb.Paper;

/**
 * Created by adityabansal on 11/11/16.
 */

public class SAVED_DATA {


    public static Set<Post> allSavedPosts = Paper.book().read("savedPosts", new LinkedHashSet<Post>());


    public static void  writeNewPost(Post nePost) {
        allSavedPosts.add(nePost);
        Paper.book().write("savedPosts", allSavedPosts);

    }


    public static void deletePost(Post item) {
        allSavedPosts.remove(item);
        Paper.book().write("savedPosts", allSavedPosts);
    }
}
