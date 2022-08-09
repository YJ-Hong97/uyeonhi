package com.kosta.uyeonhi.util;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import com.kosta.uyeonhi.applicant.Applicant;
import com.kosta.uyeonhi.likes.Likes;

@Component
public class applicantSearch {

	public static boolean findUsingIterator(
			  String userId, List<Applicant> applicant) {
			    Iterator<Applicant> iterator = applicant.iterator();
			    while (iterator.hasNext()) {
			    	Applicant applicant2 = iterator.next();
			        if (applicant2.getUser().getId().equals(userId)) {
			            return false;
			        }
			    }
			    return true;
			}

}
