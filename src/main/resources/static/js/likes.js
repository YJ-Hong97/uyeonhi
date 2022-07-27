/*
done() = success
fail() = error
*/
 $("#heart").onclick(function(){
	
	if($("#heart").hasClass()){ 
		$.ajax({
			type:"POST",
			url:"/sns/sns1/${boardId}/likes",
			datatype:""
		}).done(res=>{
			
		}).fail(error=>{
			console.log("오류",error);
		});
	}
	else{					
			$.ajax({
			type:"delete",
			url:"/sns/sns1/${boardId}/likes",
			datatype:""
		}).done(res=>{
			
		}).fail(error=>{
			console.log("오류",error);
		});
		
	}
	
});
	
