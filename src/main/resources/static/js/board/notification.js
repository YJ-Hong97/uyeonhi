
//알람목록
function alramList(){
	console.log("alramList")
	var memberId = "${login.memberId}";
	 $.ajax({
	        url : '/board/alramList',
	        type : 'get',
	        data : {'memberId' : memberId },
	        dataType : "json", 
	        success : function(data){
	         	var a='';
	         	 $.each(data, function(key, value){ 
	         		var categori = value.categori ;
	         		a += '<div>';
					a += '<div class="small text-gray-500">'+value.alramDate+'</div>';
					if(categori == "reply"){
					a += '<span class="font-weight-bold"><a href="#"  onclick="alramClick('+value.bgno+','+value.bno+',\''+value.fromId+'\');">'+value.toId+'님이 '+value.title+' 에 댓글을 달았습니다</a></span>';
					}else if(categori == "questionCheck"){
					a += '<span class="font-weight-bold"><a href="#" onclick="alramClick('+value.bgno+','+value.bno+',\''+value.fromId+'\');">'+value.toId+'님이 '+value.title+' 에 답변을 채택했습니다</a></span>';

					}
					a += '</div><hr/>';	
					
	         		 
	         		 
	         	 });
	         	 
	         	 $("#alramList").html(a);
	         	 
	        }
	        
	    
	    });
	 }
//목록끝

//목록클릭
function alramClick(bgno,bno,formId){
	console.log("alramClick")
	 $.ajax({
	        url : '/board/alramClick',
	        type : 'post',
	        data : {'memberId' : formId , 'bno':bno},
	        dataType : "json", 
	        success : function(){
	        
	        }
	        
	    
	    });
	location.href="/board/readView?bno="+bno+"&bgno="+bgno;
	
}



//알람
function alramCount(){
	console.log("alram")
	var memberId = "${login.memberId}";
	 $.ajax({
	        url : '/board/alramCount',
	        type : 'get',
	        data : {'memberId' : memberId },
	        dataType : "json", 
	        success : function(alram){
	         	console.log(alram);
	         	console.log("알람성공");
	       $('#alramCount').text(alram);
	        }
	    
	    });
}