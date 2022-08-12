function notificationMove(boardId, notiId) {
	console.log(boardId);
	console.log(notiId);

		if ($("#board-box" + boardId).offset()) {
		var offset = $("#board-box" + boardId).offset();

		$('html, body').animate({ scrollTop: (offset.top - $('.window').height() / 5) }, 400);
		$.ajax({
			url: "/api/notification/delete/" + notiId,
			type: 'DELETE',
			success: function(res) {
				console.log("알림 삭제됨");
				$('#alarmFlexBox').load(location.href + " #alarmFlexBox");
			}
		});
		
		
		
	}
	else {
		boardId -= 1;
		notiId -= 1;
		var offset = $("#board-box" + boardId).offset();

		$('html, body').animate({ scrollTop: (offset.top - $('.window').height() / 5) }, 400);
		$.ajax({
			url: "/api/notification/delete/" + notiId,
			type: 'DELETE',
			success: function(res) {
				console.log("알림 삭제됨")
				$('#alarmFlexBox').load(location.href + " #alarmFlexBox");
			}
		});
	}

	
};


function alarmSave(alarmData) {
	console.log(alarmData);

	$.ajax({
		url: '/api/notification/save',
		contentType: 'application/json',
		type: 'post',
		data: JSON.stringify(alarmData),
		dataType: "json",
		success: function(res) {
			console.log("여기까진 왔다 ㅋㅋ")
		}
	});

}



//알람목록
function alarmList() {
	console.log("alarmList")
	let memberId = $(".userSession").val();

	console.log(memberId);
	$.ajax({
		url: "/api/notification/update/" + memberId,
		type: "PUT",
		success: function(res) {
			$('.notificationList').load(location.href + " .notificationList");
		}
	})

	$.ajax({
		url: `/notification/list`,
		type: 'get',
		data: { 'userId': memberId },
		dataType: "json",
		success: function(data) {
			var a = '<div><h2 style="text-align:center;"> 알림</h2></div> ';
			if (data.length == 0) {
				a += `<div class="notification_contents">`
				a += `<div class="small text-gray-500"></div>`;
				a += `<span style="text-align:center;" class="font-weight-bold">알림이 없습니다.</span>`;
				a += `</div>`;
			};
			$.each(data, function(index, value) {
				console.log(value);
				var date = timeForToday(value.regdate);
				var categori = value.notificationType;
				if (categori == "Reply") {
					a += `<button class="notification_contents" id="${value.notification_id}" value=` + value.boardId + ` onclick="show()">`
					a += `<div class="small text-gray-500">${date}</div>`;
					a += `<span class="font-weight-bold">${value.senderId}님이 회원님의 게시물에 댓글을 달았습니다</span>`;
					a += `</button>`;
				} else if (categori == "reReply") {
					a += `<button class="notification_contents" value="${value.boardId}" onclick="show()">`
					a += `<div class="small text-gray-500">${date}</div>`;
					a += `<span class="font-weight-bold">${value.senderId}님이 회원님의 댓글에 답변을 달았습니다</span>`;
					a += `</button>`;
				} else if (categori == "Matching") {
					a += `<button class="notification_contents" href="/myPage/${'.userSession'}">`
					a += `<div class="small text-gray-500">${date}</div>`;
					a += `<span class="font-weight-bold">${value.senderId}님이 회원님에게 매칭을 신청했습니다.</span>`;
					a += `</button>`;
				}
			});
			$("#alarmList").css({ "display": "block" });
			$("#alarmFlexBox").html(a);

		}


	});
}
//목록끝



//목록클릭


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

//알람
function alarmCount() {
	console.log("alarm");
	
	$.ajax({
		url: '/notification/count',
		type: 'get',
		success: function(alarm) {
			console.log(alarm);
			console.log("알람성공");
			$('.notification_count').text(alarm);
		}

	});
}

function notificationMove2(userId, notiId) {
	console.log(userId);
	$.ajax({
		url: "/api/notification/delete/" + notiId,
		type: 'DELETE',
		success: function(res) {
			console.log("알림 삭제됨")
			location.href = "/myPage/" + userId;
		}
	});
}


