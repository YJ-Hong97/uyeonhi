$('.show').on('click', function() {
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	console.log(sequence + "," + boardId);
	
	$.ajax({
		url: `/api/reply/${boardId}`,
		type: "get",
		success: function(res) {
			console.log(res);
			let output = "";
			
			$.each(res, function(index, item){
				output+= `<div th:if="${item.depth==0}" class="replyText1">`
				output+= `<div>${item.reply_content}</div>`
				output+= `<li >${item.reply_id}<div>`
				output+= `<div class="button_box">`
				output+= `<button class="delete_reply_button" value="${item.reply_id} type="button">삭제</button>`
				output+= `<button class="update_reply_button" value="${item.reply_id}" type="button">수정</button>`
				output+= `</div>`
				output+= `<div class="reReply_box">`
				output+= `<textarea class="input_reReply"></textarea>`
				output+= `<input type="hidden" class="hidden_boardId" value="${item.boardId}"></input>`
				output+= `<button value="${item.reply_id}" class="btn_reReply" type="button">대댓글</button>`
				output+= `</div>`
				output+= `</li>`
				output+= `</div>`
				
				output+= `<div th:unless="${item.depth==0}" class="replyText2" style="margin-left:30px">`
				output+= `<div>${item.reply_content}</div>`
				output+= `<li >${item.reply_id}<div>`
				output+= `<div class="button_box">`
				output+= `<button class="delete_reply_button" value="${item.reply_id} type="button">삭제</button>`
				output+= `<button class="update_reply_button" value="${item.reply_id}" type="button">수정</button>`
				output+= `</div>`
				output+= `<div class="reReply_box">`
				output+= `<textarea class="input_reReply"></textarea>`
				output+= `<input type="hidden" class="hidden_boardId" value="${item.boardId}"></input>`
				output+= `<button value="${item.reply_id}" class="btn_reReply" type="button">대댓글</button>`
				output+= `</div>`
				output+= `</li>`
				output+= `</div>`
			});
			
			$('#here').html(output);
		},
		error: function(err) {
			alert(err);
		}
	});
});


$('.btn_reply').on('click', function() {
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
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
		},
		error: function(err) {
			alert(err);
		}
	});

});

$(document).on("click", ".btn_reReply", function() {
	let parentId = $(this).val();
	let boardId = $(".hidden_boardId").val();
	let content = $(".input_reReply").val();

	let data = {
		boardId: boardId,
		parentId: parentId,
		content: content,
		depth: 1
	};

	console.log(data);

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



$(document).on("click", ".delete_reply_button", function() {
	let replyId = $(this).val();

	console.log(replyId);

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








//document.querySelector(".show").addEventListener("click", show);
document.querySelector("#close").addEventListener("click", close);

function show() {
	document.querySelector(".background").className = "background show";
}

function close() {
	document.querySelector(".background").className = "background";
}



