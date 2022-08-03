var count = 0;
//스크롤 바닥 감지
window.onscroll = function(e) {
    //추가되는 임시 콘텐츠
    //window height + window scrollY 값이 document height보다 클 경우,
    if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
    	//실행할 로직 (콘텐츠 추가)
        count++;
        var addContent = '<div class="block"><p>'+ count +'번째로 추가된 콘텐츠</p></div>';
        //article에 추가되는 콘텐츠를 append
        $('article').append(addContent);
    }
};



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
		
		$('#sns_select').change(function(){
			
			var result = $('#sns_select option:selected').val();
			if(result == 'recruit'){
				$(".total_person").css("display", "inline");
			}else{
				$(".total_person").css("display", "none" );
			}
		});

$('.searchBtn').on('click', function() {
		$.ajax({
			url: '/sns/sns1/search',
			type: "GET",
			data: $("#search-form").serialize(),
			success: function(res) {
				$('#searchBefore').remove();
				alert("성공");
			    //화면에 출력한다.
			 var output = `
			         <p>검색결과 입니다</p>
			    `;
			    $.each(res, function(index, item){
			
				    output += `
				    <p>${item.tag}</p>
				   <p>${item.content}</p>
				   <p>${item.board_type}</p>
				   <p>${item.boardId}</p>
				   <hr>
				    `;
				});
				$("#searchContent").html(output);
			  
			   
								//<div th:each="tag : ${searchList}">
								//<p>[[${tag.boardId}]]</p>
								//<p>[[${tag.tag}]]</p>
								//<p>[[${tag.content}]]</p>
								//<p>[[${tag.board_type}]]</p>
								//</div>
								
			},
			error: function(error) {
				console.log("오류",error)
			}
		})
		
	});


	$('#btn_board').on('click', function() {
		
		let data = {
			content: $("#toggleBtn").val().trim(),
			board_type: $("#sns_select option:selected").text(),
			tag : $(".tag").val(),
			total_person : $(".total_person").val()
		};

		console.log(data);

		$.ajax({
			url: '/api/sns/boardWrite',
			contentType: 'application/json',
			data: JSON.stringify(data),
			//dataType: "json",
			type: "POST",
			success: function(res) {
				alert("게시글 등록 완료");
				location.href = "/sns/sns1";
			},
			error: function(err) {
				alert(err);
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


