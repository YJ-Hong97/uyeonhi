/**
 * 
 */
 
 $('#searchBtn').on('click', function() {
	$.ajax({
		url: '/sns/search',
		type: "GET",
		data: $("#search-form").serialize(),
		dataType:"html",
		success : function(res){
			$("#searchContent").html(res);
		},
		error : function(error){
			console.log(error);
		}
	})

});

function tag(count){
	let tagId = $("#tag_"+count);
	var tagText = tagId.text();
	var tagTextSplit = tagText.replace('#','');
	console.log(tagTextSplit);
	
	$("#searchContent").load("/sns/clickTag",tagTextSplit);
	
		$.ajax({
		url: '/sns/clickTag',
		type: "GET",
		data: tagText,
		dataType:"html",
		success : function(res){
			$("#searchContent").html(res);
		},
		error : function(error){
			console.log(error);
		}
	})

}