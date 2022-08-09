

$('#toggleBtn').click(function() {
	$('#toggleBtn').css("height", "180px");
	$('#container').css("height", "300px");
	$('.userSession').css({ "display": "inline" });
	$('#btn_board').css("display", "inline");
	$('.board_write_top').css({ "display": "flex", "justify-content": "space-between" });
	$('#sns_select').css({ "display": "inline" });
	$('#btn_fileUpload').css({ "display": "inline-block", "width": "3rem" });
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

$('#btn_board').on('click', function() {

	let data = {
		content: $("#toggleBtn").val().trim(),
		board_type: $("#sns_select option:selected").text(),
		tag: $(".tag").val(),
		total_person: $(".total_person").val(),
		deadline: $(".deadline").val()
	};

	console.log(data);

	$.ajax({
		url: '/api/sns/boardWrite',
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(data),
		type: "POST",
		success: function(res) {
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


