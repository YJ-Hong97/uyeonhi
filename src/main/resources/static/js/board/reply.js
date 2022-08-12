


$('.show').on('click', function() {
	
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	console.log(sequence + "," + boardId);

	$.ajax({
		url: `/reply/${boardId}`,
		type: "get",
		success: function(res) {
			$('#here').html(res);
		},
		error: function(err) {
			
		}
	});

	$.ajax({
		url: `/sns/modal/${boardId}`,
		type: "get",
		success: function(data) {
			var a = '';
			let str = data.image_path;
			const arr = str.split(",");
				a += `<img style="width: 100%; height: auto; max-width: 100%; max-height: 100%;" src="` + arr[0] + `"></img>`
			$(".replacePic").html(a);
			
		},
		error: function(err) {
			
		}
	})
});
$(document).on("click",'.notification_contents',function(){ 
	console.log("여까지 옴?");
	let boardId = $(this).val();
	console.log(boardId);
	$.ajax({
		url: `/reply/${boardId}`,
		type: "get",
		success: function(res) {
			$('#here').html(res);
		},
		error: function(err) {
			
		}
	});

	$.ajax({
		url: `/sns/modal/${boardId}`,
		type: "get",
		success: function(data) {
			var a = '';
			let str = data.image_path;
			const arr = str.split(",");
				a += `<img style="width: 100%; height: auto; max-width: 100%; max-height: 100%;" src="` + arr[0] + `"></img>`
			$(".replacePic").html(a);
			
		},
		error: function(err) {
			
		}
	})
});

$(document).on("click",'.p-2',function(){ 
	console.log("여까지 옴?");
	let boardId = $(this).val();
	console.log(boardId);
	$.ajax({
		url: `/reply/${boardId}`,
		type: "get",
		success: function(res) {
			$('#here').html(res);
		},
		error: function(err) {
			
		}
	});

	$.ajax({
		url: `/sns/modal/${boardId}`,
		type: "get",
		success: function(data) {
			var a = '';
			let str = data.image_path;
			const arr = str.split(",");
				a += `<img style="width: 100%; height: auto; max-width: 100%; max-height: 100%;" src="` + arr[0] + `"></img>`
			$(".replacePic").html(a);
			
		},
		error: function(err) {
			
		}
	})
});



$('.btn_reply').on('click', function() {

	console.debug("reply.socket", websocket)

	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	let writer = $(".userSession").val();
	let readWriter = $(".boardWriter" + boardId).text();

	let alramData = {
		senderId: writer,
		receiverId: readWriter,
		boardId: boardId,
		notificationType: "Reply"
	}

	let data = {
		boardId: boardId,
		content: $(".reply_content" + sequence).val().trim(),
		depth: 0
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

			if (readWriter != writer) {

				alarmSave(alramData);

				if (websocket) {
					let socketMsg = "reply," + readWriter + "," + writer + "," + boardId;
					console.log(socketMsg);
					websocket.send(socketMsg);
				}
			}
		},
		error: function(err) {
			
		}
	});

});



$(document).on("click", ".btn_reReply", function() {

	console.debug("reply.socket", websocket)

	let parentId = $(this).val();
	let boardId = $(".hidden_boardId").val();
	let content = $(".input_reReply" + parentId).val();
	let replyWriter = $(".hidden_Writer" + parentId).val();
	let reReplyWriter = $(".userSession").val();



	let data = {
		boardId: boardId,
		parentId: parentId,
		content: content,
		depth: 1
	};

	let alarmData = {
		senderId: reReplyWriter,
		receiverId: replyWriter,
		boardId: parentId,
		notificationType: "reReply"
	}

	console.log(data);
	console.log("작성자 이름" + replyWriter);
	console.log("내 이름" + reReplyWriter);

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
			alarmSave(alarmData);


			if (replyWriter != reReplyWriter) {
				if (websocket) {
					let socketMsg = "reReply," + replyWriter + "," + reReplyWriter + "," + parentId;
					console.log(socketMsg);
					websocket.send(socketMsg)
				}
			}

		},
		error: function(err) {
			
		}
	});


});



$(document).on("click", ".delete_reply_button", function() {
	let replyId = $(this).val();


	$.ajax({
		url: `/api/reply/replyDelete/${replyId}`,
		contentType: 'application/json',
		dataType: "json",
		type: "DELETE",
		success: function(res) {
			alert("댓글 삭제 완료");
			location.href = "/sns/sns1";
		},
		error: function(err) {
			alert(JSON.stringify(err));
		}
	})
});

$(document).on("click", ".update_reply_button", function() {
	let replyId = $(this).val();


	$("#reply_origin_box" + replyId).css("display", "none");
	$("#reply_update_box" + replyId).css("display", "block");
	$(".reply_update_cancel").on('click', function() {
		$("#reply_origin_box" + replyId).css("display", "flex");
		$("#reply_update_box" + replyId).css("display", "none");

	});


	$(".reply_update_ok" + replyId).on('click', function() {

		let updateCon = $("#reply_update_textarea" + replyId).val();

		$.ajax({
			url: `/api/reply/replyUpdate/${replyId}`,
			data: { updateCon: updateCon },
			dataType: "json",
			type: "PUT",
			success: function(res) {
				alert("댓글 수정 완료");
				location.href = "/sns/sns1";
			},
			error: function(err) {
				alert(JSON.stringify(err));
			}
		});
	});
});










//document.querySelector(".show").addEventListener("click", show);
document.querySelector("#close").addEventListener("click", close);

function show() {
	document.querySelector(".background").className = "background show";
}

function close() {
	document.querySelector(".background").className = "background";
}

