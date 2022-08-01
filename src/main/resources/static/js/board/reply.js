
$('.btn_reply').on('click', function() {
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	let data = {
		content: $(".reply_content" + sequence).val().trim(),
	};

	if (data.content == "") {
		alert("댓글을 입력해 주세요");
		return;
	}

	$.ajax({
		url: '/api/reply/' + boardId,
		contentType: 'application/json',
		data: JSON.stringify(data),
		//dataType: "json",
		type: "POST",
		success: function(res) {
			alert("댓글 등록 완료");
			location.href = "/sns/sns1";
		},
		error: function(err) {
			alert(err);
		}
	});

});





$('.reply_show').on('click', function() {

	

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
	let seqence = $(this).attr('aa');
	let boardContent = $('#modify_' + seqence).val();

	data = {
		board_id: boardId,
		seq: seqence,
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


