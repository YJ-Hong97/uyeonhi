

$('#toggleBtn').click(function() {
	$('#toggleBtn').css("height", "180px");
	$('#container').css("height", "300px");
	$('.userSession').css({ "display": "inline" });
	$('#btn_board').css("display", "block");
	$('.board_write_top').css({ "display": "flex", "justify-content": "space-between" });
	$('#sns_select').css({ "display": "inline" });
	$('#btn_fileUpload').css({
		"display": "block",
		"padding": "6px 25px",
		"background-color": "rgb(142 85 255)",
		"border-radius": "4px",
		"color": "white",
		"cursor": "pointer;"
	});
	$('.file_image').css({ "display": "inline" });
	$('.tag').css("display", "inline");
});

$('#sns_select').change(function() {

	var result = $('#sns_select option:selected').val();
	if (result == 'recruit') {
		$(".total_person").css("display", "inline");
		$(".deadline").css("display", "inline");
		$('#container').css("height", "330px");
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


