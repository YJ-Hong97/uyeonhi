package com.kosta.uyeonhi.util;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import com.kosta.uyeonhi.likes.Likes;

@Component
public class LikeSearch {

	public static boolean findUsingIterator(
			  String userId, List<Likes> likes) {
			    Iterator<Likes> iterator = likes.iterator();
			    while (iterator.hasNext()) {
			    	Likes like = iterator.next();
			        if (like.getUser().getId().equals(userId)) {
			            return false;
			        }
			    }
			    return true;
			}

}
