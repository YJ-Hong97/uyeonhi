/*
done() = success
fail() = error
*/
function likes(boardId){
	let heart = $("#heart_"+boardId);
	
	
	
	if(heart.hasClass("likes")){ 
		$.ajax({
			type:"POST",
			url:"/api/sns/likes/" + boardId
		}).done(res=>{
			alert(res);
			//heart.css("font-variation-settings","'FILL' 1");
			//heart.css("color","red");
			heart.addClass("notlikes")
			heart.removeClass('likes');
		}).fail(error=>{
			console.log("오류",error);
		});
	}
	else if(heart.hasClass("notlikes")){					
			$.ajax({
			type:"DELETE",
			url:"/api/sns/notlikes/" + boardId
		}).done(res=>{
			alert(res);
			heart.css("font-variation-settings","'FILL' 0");
			heart.css("color","black");
			heart.removeClass('notlikes');
			heart.addClass('likes');
			//location.href = "/sns/sns1";
		}).fail(error=>{
			console.log("오류",error);
		});
		
	}
	
}
