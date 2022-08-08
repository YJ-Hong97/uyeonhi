/**
 * 
 */
 
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
		},
		error : function(error){
			console.log(error);
		}
	})

}

function apply(boardId){
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
	
};
