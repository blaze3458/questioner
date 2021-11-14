
$(document).ready(function() {
	initializeExamCounter();
	initializeUniversityList();
});

$(document).on("click",'#signUpForm',function(){
    $('.fix-full').first().toggleClass("show");
    $('.limiter').first().toggleClass("limitter-blur");
    $('.reg-form').first().toggleClass("zoomIn");
    $('.reg-form').first().toggleClass("zoomOut");
});

$(document).on("click", '#closeSignUpForm', function(){
    $('.fix-full').first().toggleClass("show");
    $('.limiter').first().toggleClass("limitter-blur");
    $('.reg-form').first().toggleClass("zoomIn");
    $('.reg-form').first().toggleClass("zoomOut");
});

$(document).on("submit",'#registerForm', function(){
	event.preventDefault();

	var _data = {
		email : $('#registerForm [name="email"]').val(),
		name : $('#registerForm [name="name"]').val(),
		surname : $('#registerForm [name="surname"]').val(),
		password : $('#registerForm [name="password"]').val(),
		type : $('#registerForm [name="type"]').val(),
		check : $('#registerForm [name="check"]').prop("checked")
	};
		
	$('.abs-bg-blur').toggleClass("show");
	$.ajax({
  		url: "/register",
	 	type: "post",
  		context: document.body,
		data: _data
	}).done(function(response,text,jqXHR) {
  		$('.abs-bg-blur').toggleClass("show");
	
		if(response.errorCode){
			showResponseMessage(false, response.errorMsg)
		}
		else{
			window.location.replace("/");
		}
			
	}).fail(function(xhr, textStatus, error){
		$('.abs-bg-blur').toggleClass("show");
	});
});

$(document).on("change", '[name=university]', function(){
	
	var uni = $(this).val();
	var elm = $('[name=faculty]').first();
	var depElm = $('[name=department]').first();
	
	$.ajax({
		url: "/faculty?university_id="+uni,
		type:"GET",
		contentType: "application/json; charset=utf-8",
	}).done(function(response,text,jqXHR){
		$(elm).empty().append('<option value="none" selected>Seçiniz</option>');
		$(depElm).empty().append('<option value="none" selected>Seçiniz</option>');
		for(var i = 0; i < response.length; ++i){
			var faculty = response[i];
			
			var opt = $('<option></option>');
			$(opt).attr("value",faculty.id);
			$(opt).text(faculty.name);
			$(elm).append(opt);
		}
		
	}).fail(function(xhr,textStatus,error){
		console.log(error);
	});
});

$(document).on("change", '[name=faculty]', function(){
	
	var fac = $(this).val();
	var elm = $('[name=department]').first();
	
	$.ajax({
		url: "/department?faculty_id="+fac,
		type:"GET",
		contentType: "application/json; charset=utf-8",
	}).done(function(response,text,jqXHR){
		$(elm).empty();
		$(elm).append('<option value="none" selected>Seçiniz</option>');
		for(var i = 0; i < response.length; ++i){
			var department = response[i];
			
			var opt = $('<option></option>');
			$(opt).attr("value",department.id);
			$(opt).text(department.name);
			$(elm).append(opt);
		}
		
	}).fail(function(xhr,textStatus,error){
		console.log(error);
	});
});

function showResponseMessage(success, errorMsg){
  var alert = document.createElement('div');

  if(success)
    $(alert).addClass('alert alert-success float-right');
  else
    $(alert).addClass('alert alert-warning float-right');

  	$(alert).html(errorMsg);
  	$('#alert').append(alert);
  	$('#alert').toggleClass('d-inline-flex');
	$('#alert').toggleClass('d-none');

  setTimeout(() => {
    $('#alert').toggleClass('d-inline-flex');
	$('#alert').toggleClass('d-none');
    $('#alert').empty();
  }, (3000));
}

function initializeExamCounter(){
	$("[data-timeout]").map((e,k) => {
		var id = $(k).data('timeout');
		var timeout = $(k).data('timeout');
		var startTime = $(k).data('starttime');
		counter(k,id,startTime, timeout);
	});
}

function counter(elm,id, start, timeout){
	
	var startDate = new Date(start);
	var curDate = new Date();
	
	var elapsed = startDate - curDate;
	var second = elapsed / 1000;
	var text ="";
	if(elapsed < 0)
		return; // id den link oluştur
	
	if(second >= 3600*24){
		var day = Math.floor(second / (3600*24));
		text += (day+" Gün");
	}
		
	if(second >= 3600){
		var hour = Math.floor((second / 3600)%24);
		text += (" "+hour+" Saat");
	}
		
	if(second >= 60){
		var min = Math.floor((second / 60)%60);
		text +=(" "+min+ " Dakika");
	}
	
	second = Math.floor((second % 60))	
		
	text += (" "+second+" Saniye");
	
	$(elm).html(text);
	
	setTimeout(()=> {counter(elm,id,start,timeout)},1000);
}

function initializeUniversityList(){
	var elm = $('[name="university"]').first();
	$.ajax({
  			url: "/json/university.json",
	 		type: "get",
  			context: document.body,
			contentType: "application/json; charset=utf-8",
			dataType:'json'
		}).done(function(response,text,jqXHR) {
  			var universities = response.university;
			for(var i = 0 ; i < universities.length; ++i){
				var uni = universities[i];
				var opt = $('<option></option>');
				$(opt).attr("value",uni.id);
				$(opt).text(uni.name);
				$(elm).append(opt);
			}
			
		}).fail(function(xhr, textStatus, error){
			console.log(xhr);
		});
}

