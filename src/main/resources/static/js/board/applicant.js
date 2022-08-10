/**
 * 
 */
 
 function apply(boardId){
	
	const deadline = new Date($("#deadline_"+boardId).text().replace('마감일 : ','').trim());
	console.log(deadline);
	
	const today = new Date();
	console.log(today.toString());
	
	const Datecomparison = (deadline,today) =>{
		return deadline.getFullYear() >= today.getFullYear()
		 	   && deadline.getMonth() >= today.getMonth()
		 	   && deadline.getDate() > today.getDate();
	}
	
	console.log(Datecomparison(deadline, today));
	
	var applicantPerson = $("#applicant_person_" + boardId).text().replace('신청 현황 : ','').trim();
	var totalPerson = $("#total_person_" + boardId).text().replace('/','').trim();
	
	let applyBtn = $("#applyBtn_"+boardId);
	
	if(applicantPerson<totalPerson && applyBtn.hasClass("apply") && Datecomparison(deadline, today)){
			$.ajax({
			type:"POST",
			url:"/api/sns/applicant/" + boardId
		}).done(res=>{
			
			alert(res);
			applyBtn.removeClass("apply");
			applyBtn.addClass("cancleApply");
			 location.reload();

		}).fail(error=>{
			console.log("오류",error);
		});
	}else if(applyBtn.hasClass("cancleApply")){
		
		if(Datecomparison(deadline, today)){
			$.ajax({
			type:"DELETE",
			url:"/api/sns/applicantCancle/" + boardId
		}).done(res=>{
			alert(res);
			applyBtn.removeClass("cancleApply");
			applyBtn.addClass("apply");
			 location.reload();
		}).fail(error=>{
			console.log("오류",error);
		});
		}else{
			alert("당일 취소는 불가합니다");
		}

	}		
	
}