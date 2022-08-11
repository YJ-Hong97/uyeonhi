function notificationMove(boardId) {
	console.log(boardId);
	 var offset = $("#board-box" + boardId).offset();
        $('html, body').animate({scrollTop : (offset.top - $('.window').height() / 5)}, 400);
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
function alramList() {
	console.log("alramList")
	let memberId = $(".userSession").text();
	$.ajax({
		url: `/notification/list`,
		type: 'get',
		data: { 'userId': memberId },
		dataType: "json",
		success: function(data) {
			var a = '<div><h2 style="text-align:center;"> 알림</h2></div> ';
			$.each(data, function(index, value) {
				var date = timeForToday(value.regdate);
				var categori = value.notificationType;
				a += `<div class="notification_contents" onclick="notificationMove(${value.boardId})">`
				a += `<div class="small text-gray-500">${date}</div>`;
				if (categori == "Reply") {
					a += `<span class="font-weight-bold"><a href="#"  onclick="alramClick();">${value.senderId}님이 회원님의 게시물에 댓글을 달았습니다</a></span>`;
					a += `</div>`;
				} else if (categori == "reReply") {
					a += `<span class="font-weight-bold"><a href="#"  onclick="alramClick();">${value.senderId}님이 회원님의 댓글에 답변을 달았습니다</a></span>`;
					a += `</div>`;
				}
			});
			$("#alarmList").css({ "display": "block" });
			$("#alarmFlexBox").html(a);

		}


	});
}
//목록끝



//목록클릭
function alramClick(bgno, bno, formId) {
	console.log("alramClick")
	$.ajax({
		url: '/board/alramClick',
		type: 'post',
		data: { 'memberId': formId, 'bno': bno },
		dataType: "json",
		success: function() {

		}


	});
	location.href = "/board/readView?bno=" + bno + "&bgno=" + bgno;

}

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
function alramCount() {
	console.log("alram")
	var memberId = "${login.memberId}";
	$.ajax({
		url: '/board/alramCount',
		type: 'get',
		data: { 'memberId': memberId },
		dataType: "json",
		success: function(alram) {
			console.log(alram);
			console.log("알람성공");
			$('#alramCount').text(alram);
		}

	});
}