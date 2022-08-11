

$('#toggleBtn').click(function() {
	$('#toggleBtn').css("height", "180px");
	$('#container').css("height", "310px");
	$('.userSession').css({ "display": "inline" });
	$('#btn_board').css({"display": "block","float":"right","margin-right":"10px"});
	$('.board_write_top').css({ "display": "block", "float":"right", "width":"80%", "margin-right" : "10px","height":"100px","margin-top":"8px"});
	$('#sns_select').css({ "display": "inline","float":"left","margin-top":"3px"});
/*	$('#btn_fileUpload').css({
		"display": "block",
		"width" : "100%",
		"padding": "6px 25px",
		"background-color": "rgb(142 85 255)",
		"border-radius": "4px",
		"color": "white",
		"cursor": "pointer;"
	});*/
	$('.file_image').css({ "display": "inline" });
	$('.tag').css("display", "inline");
	$('label').css("width", "32px");
	$('.upload-btn').css({"display": "inline","float":"left","margin-left":"10px"});
	$('.board_info ').css({"width":"300px"});
	
});

$('#sns_select').change(function() {

	var result = $('#sns_select option:selected').val();
	if (result == 'recruit') {
		$(".total_person").css("display", "inline");
		$(".total_person").css("height", "29px");
		$(".deadline").css("display", "inline");
		$('#container').css("height", "335px");
	} else {
		$(".deadline").css("display", "none");
		$(".total_person").css("display", "none");
	}
});

function setDetailImage(event) {

}


$('#btn_board').on('click', function() {
	var formData = new FormData();

	let data = {
		content: $("#toggleBtn").val().trim(),
		board_type: $("#sns_select option:selected").text(),
		tag: $(".tag").val(),
		total_person: $(".total_person").val(),
		deadline: $(".deadline").val(),
	};

	var fileInput = $('#btn_fileUpload');
	// fileInput 개수를 구한다.
	for (var i = 0; i < fileInput.length; i++) {
		if (fileInput[i].files.length > 0) {
			for (var j = 0; j < fileInput[i].files.length; j++) {
				console.log(" fileInput[i].files[j] :::" + fileInput[i].files[j]);

				// formData에 'file'이라는 키값으로 fileInput 값을 append 시킨다.  
				formData.append('file', $('#btn_fileUpload')[i].files[j]);
			}
		}
	}


	formData.append('key', new Blob([JSON.stringify(data)], { type: "application/json" }));

	for (let key of formData.keys()) {
		console.log(key);
	}

	/* value 확인하기 */
	for (let value of formData.values()) {
		console.log(value);
	}

	$.ajax({
		url: '/api/sns/boardWrite',
		processData: false,
		contentType: false,
		enctype: 'multipart/form-data',
		data: formData,
		type: "POST",
		success: function() {
			alert("게시글 등록 완료");
			location.href = "/sns/sns1";
		},
		error: function(err) {
			alert(JSON.stringify(err));
		}
	})

});

$('.delete_button').on('click', function() {
	let board_id = $(this).val();

	console.log(board_id);

	$.ajax({
		url: '/api/sns/boardDelete/' + board_id,
		contentType: 'application/json',
		dataType: "json",
		type: "DELETE",
		success: function(res) {
			alert("게시글 삭제 완료");
			location.href = "/sns/sns1";
		},
		error: function(err) {
			alert(JSON.stringify(err));
		}
	})
});



$('.update_button').on('click', function() {

	let boardId = $(this).val();
	let sequence = $(this).attr('aa');
	let boardContent = $('#modify_' + sequence).val();

	data = {
		board_id: boardId,
		seq: sequence,
		content: boardContent
	}

	$.ajax({
		url: '/api/sns/boardUpdate/' + boardId,
		data: JSON.stringify(data),
		contentType: 'application/json',
		dataType: "json",
		type: "PUT",
		success: function(res) {
			alert("게시글 수정 완료");
			location.href = "/sns/sns1";
		},
		error: function(err) {
			alert(JSON.stringify(err));
		}
	})
});


function timeForToday(value) {
	const today = new Date();
	const timeValue = new Date(value);

	const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
	if (betweenTime < 1) return '방금전';
	if (betweenTime < 60) {
		return `${betweenTime}분전`;
	}

	const betweenTimeHour = Math.floor(betweenTime / 60);
	if (betweenTimeHour < 24) {
		return `${betweenTimeHour}시간전`;
	}

	const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
	if (betweenTimeDay < 365) {
		return `${betweenTimeDay}일전`;
	}

	return `${Math.floor(betweenTimeDay / 365)}년전`;
}

