/**
 * 
 */

  $('#searchTag').autocomplete({
	 source : function(request, response) { //source: 입력시 보일 목록
	     $.ajax({
	           url : "/autocomplete"   
	         , dataType: "JSON"
	         , data : {searchValue: request.term}	// 검색 키워드
	         , success : function(result){ 	// 성공
	             response(
	                 $.map(result, function(item) {
	                     return {
	                    	     label : item.data    	// 목록에 표시되는 값
	                           , value : item.data		// 선택 시 input창에 표시되는 값
	                           
	                     };
	                 })
	             );    //response
	         }
	         ,error : function(){ //실패
	             alert("오류가 발생했습니다.");
	         }
	     });
	 }
		,focus : function(event, ui) { // 방향키로 자동완성단어 선택 가능하게 만들어줌	
			return false;
		},
		minLength: 1,// 최소 글자수
		delay: 100	//autocomplete 딜레이 시간(ms),
		, select : function(event, ui) { 
      	// 아이템 선택시 실행 ui.item 이 선택된 항목을 나타내는 객체, lavel/value/idx를 가짐
			console.log(ui.item.label);
	 }
});
 
 $('#searchBtn').on('click', function() {
	$.ajax({
		url: '/sns/search',
		type: "GET",
		data: $("#search-form").serialize(),
		dataType:"html",
		success : function(res){
			$("#searchContent").html(res);
		},
		error : function(error){
			console.log(error);
		}
	})

});

function tag(boardId,count,index){
	var number = boardId + count + index
	let tagId = $("#tag_"+number);
	var tagText = tagId.text();
	var tagTextSplit = tagText.replace('#','').trim();
	console.log(tagTextSplit);
	
	
	
		$.ajax({
		url: '/sns/clickTag',
		type: "POST",
		data: {tag:tagTextSplit},
		dataType:"text",
		success : function(res){
			$("#searchContent").html(res);
			$(".board_tag").css("font-size","14.3893px");
		},
		error : function(error){
			console.log(error);
		}
	})

}

/*function apply(boardId){
	var applicantPerson = $("#applicant_person_" + boardId).text();
	var totalPerson = $("#total_person_" + boardId).text();
	
	data = {applicant_person : applicantPerson+1,
				board_id : boardId};
	
	if(applicantPerson<totalPerson){
		$.ajax({
		url: '/api/sns/recruitApply/' + boardId,
		data: JSON.stringify(data),
		contentType: 'application/json',
		dataType: "json",
		type: "PUT",
		success: function(res) {
			alert("신청 완료");
			location.href = "/sns/sns1";
		},
		error: function(error) {
			console.log("오류",error);
		}
	})
		
	}else{
		alert("모집이 마감 되었습니다");
		
	}
	
};*/
