$(document).ready(function(){
	
	$('.table .eBtn').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href,function(platform,status){
			
			$('.myForm #id').val(platform.id);
			$('.myForm #name').val(platform.name);
			$('.myForm #price').val(platform.price);
			$('.myForm #image').val(platform.image);
			
		});
		
		$('.myForm #exampleModal').modal();
		
	});
});