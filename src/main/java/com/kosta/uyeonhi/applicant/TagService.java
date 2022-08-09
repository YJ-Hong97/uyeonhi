package com.kosta.uyeonhi.applicant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.sns.Board;

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;

	public JSONArray autoSearch(String searchValue) throws IOException {
		 
		 
	    JSONArray arrayObj = new JSONArray();
	    JSONObject jsonObj = null; 
	    ArrayList<String> arrayList = new ArrayList<String>(); 
	 
	    List<Board> board = tagRepository.findByTagContaining(searchValue, Sort.by(Sort.Direction.ASC, "boardId"));
	 
	    //db tag 데이터 전부 자르기
	    for(Board tag : board) { 
	        String str = tag.getTag();
	        System.out.println(str);
	        
	        	String[] arr = str.split(",");
	        	
	        	for(String string : arr) {
	        		System.out.println("string : "+string);
	        		arrayList.add(string); 
	        	}	               
	    } 
	    
	    System.out.println(arrayList);	  
	    
	    //중복제거    
	    List<String> resultList = new ArrayList<String>();
	    for(String item : arrayList) {
	    	if(!resultList.contains(item)) {
	    		resultList.add(item);
	    		
	    	}
	    }
	    System.out.println(resultList);
	    
	    //입력 내용만 나오도록
	    Iterator<String> it = resultList.iterator();
	    while(it.hasNext()) {
	    	
	    	String value = it.next();
	    	if(!value.contains(searchValue)) {
	    		it.remove();
	    	}
	    }
	    
	    System.out.println(resultList);
	    
	    
	   
	  
	    //뽑은 후 json파싱 
	    for(String str : resultList) {
	    		jsonObj = new JSONObject();
	   	        jsonObj.put("data", str);
	   	        arrayObj.add(jsonObj); 
	    }
	    System.out.println(arrayObj);
	 
	    return arrayObj;
	 
	}
}
