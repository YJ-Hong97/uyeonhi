
function alarmSave(alarmData){
	console.log(alarmData);

	$.ajax({
		url : '/api/notification/save',
		contentType: 'application/json',
		type : 'post',
		data : JSON.stringify(alarmData),
		dataType : "json",
		success : function(res){
			console.log("여기까진 왔다 ㅋㅋ")
		}
	});
	
}



//알람목록
function alramList(){
	console.log("alramList")
	
	let memberId = $(".userSession").text();
	 $.ajax({
	        url : `/notification/list`,
	        type : 'get',
	        data : {'userId' : memberId },
	        dataType : "json", 
	        success : function(data){
	         	var a='';
	         	 $.each(data, function(index, value){ 
	         		var categori = value.notificationType ;
	         		a += `<div>`
	         		a += `<div class="small text-gray-500">${value.regdate}</div>`;
	         		if(categori == "Reply"){
					a += `<span class="font-weight-bold"><a href="#"  onclick="alramClick();">${value.senderId}님이 회원님의 게시물에 댓글을 달았습니다</a></span>`;
	         		a += `</div>`
					}
	         		 
	         		 
	         	 });
	         	 
	         	 $("#alarmList").html(a);
	         	 
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