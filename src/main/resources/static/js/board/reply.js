



$('.show').on('click', function() {
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	console.log(sequence + "," + boardId);

	$.ajax({
		url: `/reply/${boardId}`,
		type: "get",
		success: function(res) {
			$('#here').html(res);


			/*let output = "";
			$.each(res, function(index, item) {
				if (item.depth == "0") {
					output += `<li>`
					output += `<div class="replyText1">`
					output += `<div>${item.reply_content}</div>`
					output += `<div>${item.depth}</div>`
					output += `<div class="button_box">`
					output += `<button class="delete_reply_button" value="${item.reply_id}" type="button">삭제</button>`
					output += `<button class="update_reply_button" value="${item.reply_id}" type="button">수정</button>`
					output += `</div>`
					output += `<div class="reReply_box">`
					output += `<textarea class="input_reReply"></textarea>`
					output += `<input type="hidden" class="hidden_boardId" value="${item.boardId}"></input>`
					output += `<button value="${item.reply_id}" class="btn_reReply" type="button">대댓글</button>`
					output += `</div>`
					output += `<div th:class="there${item.reply_id}">`
					output += `</div>`
					output += `</li>`
				}
			});

			$('#here').html(output); */



		},
		error: function(err) {
			alert(err);
		}
	});
});


$('.btn_reply').on('click', function() {
	
	console.debug("reply.socket", websocket)
	
	let sequence = $(this).val();
	let boardId = $(".board" + sequence).val();
	let writer = $(".userSession").text();
	let readWriter = $(".boardWriter"+boardId).text();
	
	let alramData = {
		senderId : writer,
		receiverId : readWriter,
		boardId : boardId,
		notificationType : "Reply"
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
			
			if(readWriter != writer){
				
				alarmSave(alramData);
				
           		if(websocket){
        			let socketMsg = "reply,"+readWriter+","+writer+","+boardId;
        			console.log(socketMsg);
        			websocket.send(socketMsg);
           		}
           	}
		},
		error: function(err) {
			alert(err);
		}
	});

});



$(document).on("click", ".btn_reReply", function() {
	
	console.debug("reply.socket", websocket)
	
	let parentId = $(this).val();
	let boardId = $(".hidden_boardId").val();
	let content = $(".input_reReply" + parentId).val();
	let replyWriter = $(".hidden_Writer" + parentId).val();
	let reReplyWriter = $(".userSession").text();

	

	let data = {
		boardId: boardId,
		parentId: parentId,
		content: content,
		depth: 1
	};
	
	let alarmData = {
		senderId : reReplyWriter,
		receiverId : replyWriter,
		boardId : parentId,
		notificationType : "reReply"
	}

	console.log(data);
	console.log("작성자 이름"+replyWriter);
	console.log("내 이름"+reReplyWriter);

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

			
			if(replyWriter != reReplyWriter){
           		if(websocket){
        			let socketMsg = "reReply,"+replyWriter+","+reReplyWriter+","+parentId;
        			console.log(socketMsg);
        			websocket.send(socketMsg)
           		}
           	}
			
		},
		error: function(err) {
			alert(err);
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


//document.querySelector(".show").addEventListener("click", show);
document.querySelector("#close").addEventListener("click", close);

function show() {
	document.querySelector(".background").className = "background show";
}

function close() {
	document.querySelector(".background").className = "background";
}



