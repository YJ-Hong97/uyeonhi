/**
 * 
 *///아는 사람 만나지 않기
		var setting = document.querySelector(".settingContainer table");
		var block = document.querySelector(".blockContainer");
		$('#block').click(function(event) {
			$.ajax({
				url : "notToMeet",
				type : "post",
				success : function(responseData) {
					var output = "<tr><th>차단된 번호</th><th></th><th><span class='material-symbols-outlined' id = 'close'>close</span></th></tr>";
					for(let i in responseData){
						output += `					
							<tr>
								<td>${responseData[i].phone}</td>
								<td>(${responseData[i].name})</td>
								<td><button type = "button" class = "delete" value = ${responseData[i].phone}>삭제하기</button></td>
							</tr>`;
					}
					$("#tblBlock").html(output);
					
				},
				error:function(e){
					console.log(e);
				}
			}); 
			setting.style.display = "none";
			block.style.display = "block";
			
		});
		//아는 사람 등록
		$('#bsubmit').click(function(){
			let bname = $('#bname').val();
			let bphone = $('#bphone').val();
			$.ajax({
				url:"/newBlock",
				type:"post",
				data:{"bname":bname,"bphone":bphone},
				success:function(response){
					if(response=="fail"){
						alert("이미 등록된 사람입니다.");
					}else{
						setting.style.display = "block";
						block.style.display = "none";
					}
				}
			});
			
		});
		//아는 사람 삭제
		
		$(document).on("click", ".delete", function() {
			let dvalue = $(this).val();
			$.ajax({
				url:"/dBlock/"+dvalue,
				type:"post"
			});
			setting.style.display = "block";
			block.style.display = "none";
		});
		//아는 사람 모달 끄기
		$(document).on("click","#close",function(){
			setting.style.display = "block";
			block.style.display = "none";
		});
		//고객센터
		$('#inquiry').click(function(){
			location.href = "/inquiry";
		});
		
		
		////계정설정
		$('#accounting').change(function(){
			if($(this).val()=="aboutMe"){
				location.href = "/aboutMe";
				
			}else if($(this).val()=="aboutYou"){
				location.href = "/aboutYoui";
				
			}else if($(this).val()=="passwordUpdate"){
				
			}else if($(this).val()=="secession"){
				
			}
		});