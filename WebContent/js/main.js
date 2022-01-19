$(window).on("beforeunload",function(e){
    console.log(location);
    var path = location.pathname;
    
    if(path === "/exam"){
		saveLastExamSituation();
	}
    	
});

$(document).ready(function() {
	initializeExamCounter();
	initializeUniversityList();
});

$(document).on('click','body', function(){
	closeSearchResults();
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

$(document).on("keyup",'#profile_search',function(){
	var value = $(this).val();
	var section = $(this).data('section');
	
	closeSearchResults();
	
	if(value.length < 4)
		return;
	
	$.ajax({
		url: "/search_profile?section="+section+"&value="+value,
		type:"GET",
		contentType: "application/json; charset=utf-8",
	}).done(function(response,text,jqXHR){
		
		if(response.values.length <= 0)
			return;
			
		if(response.section === "exams"){
			response.values.forEach(k=>{
				var row = $('<div class="row pt-2 pb-2 fs-14 border-bottom"></div>');
				var col = $('<a class="col"></a>');
				$(col).text(k.header);
				$(col).attr('href',"/exam/"+k.id);
				$(row).append(col);
				$('#search_results').append(row);
			});
		}
		else if(response.section === "teachers"){
			response.values.forEach(k=>{
				var row = $('<div class="row pt-2 pb-2 fs-14 border-bottom"></div>');
				var row2 = $('<div class="d-flex align-items-center justify-content-start"></div>');
				var col = $('<a class="col"></a>');
				var img = $('<img class="img-thumbnail rounded-circle me-1" width=32 height=32 />');
				var name = $('<div class="col"></div>');
				
				$(img).attr('src', k.profile);
				$(col).attr('href',"/teacher/"+k.id);
				$(name).text(k.fullname);
				
				$(row2).append(img);
				$(row2).append(name);
				$(col).append(row2);
				$(row).append(col);
				$('#search_results').append(row);
			});
		}
		
		$('#search-container').addClass('show');
	}).fail(function(xhr,textStatus,error){
		console.log(xhr);
	});
});

$(document).on("click", '#exams-tab,#teachers-tab', function(){
	var section = $(this).data('bs-target');
	section = section.slice(1,section.length);
	$('#profile_search').data('section',section);
});

$(document).on("click", "#change_bio", function(){
	$('#bio').attr('contenteditable',true);
	$('#bio').data('old_bio',$('#bio').text());
});

$(document).on("focusout", '#bio', function(){
	var edit = $('#bio').data('edit');
	$('#bio').attr('contenteditable',false);
	var old_bio = $('#bio').data('old_bio');
	var new_bio = $('#bio').text();
	
	var _data = {
		old : old_bio,
		new : new_bio
	};
	
	console.log(_data);
	
	if(old_bio != new_bio){
		$.ajax({
			url:"/change_bio",
			type:"POST",
			context: document.body,
			data: _data
		}).done(function(response,text,jqXHR){
			showResponseMessage(response.errorCode,response.errorMsg);
			
		}).fail(function(xhr,textStatus,error){
			console.log(xhr);
		});
	}
});

$(document).on('click','#next_question', function(){
	
	
	$.ajax({
			url:"/next_question",
			type:"POST",
			context: document.body,
			data: _data
		}).done(function(response,text,jqXHR){
			showResponseMessage(response.errorCode,response.errorMsg);
			
		}).fail(function(xhr,textStatus,error){
			console.log(xhr);
		});
});

$(document).on('click','#prev_question', function(){
	
	
	$.ajax({
			url:"/prev_question",
			type:"POST",
			context: document.body,
			data: _data
		}).done(function(response,text,jqXHR){
			showResponseMessage(response.errorCode,response.errorMsg);
			
		}).fail(function(xhr,textStatus,error){
			console.log(xhr);
		});
});

$(document).on('click','.btn-answer',function(){
	var _key = $(this).data('key');
	var _value = $(this).data('value');
	console.log(_key+':'+_value);
	
	var data = {
		index:_key,
		value:_value
	}
	
	$('#exam-answers').data('selected',data);
});

$('[name=avatar]').on('change',(e) => {
	var files =  e.currentTarget.files;
    var filesize = ((files[0].size/1024)/1024).toFixed(4);
    var titles = $('body').data('titles');
        if(filesize > 64){
            showResponseMessage(false,"Lütfen daha düşük boyutta bir resim seçiniz.");
            e.currentTarget.files = null;
        }else{
			data = new FormData();
    		data.append('file', e.currentTarget.files[0]);
			$.ajax({
				url:"/change_avatar",
				type:"POST",
				enctype: 'multipart/form-data',
				cache:false,
           	 	processData: false,
				contentType: false,
				data:data,
			}).done(function(response,text,jqXHR){
				if(response.errorCode){
					var img = document.getElementById("avatar-big");
           			imgUrl = URL.createObjectURL(e.currentTarget.files[0]);
           			img.src = imgUrl;
				}				
          		showResponseMessage(response.errorCode,response.errorMsg);

			}).fail(function(xhr,textStatus,error){
				showResponseMessage(false,"Bir hata ile karşılaşıldı.Lütfen daha sonra tekrar deneyin.");
			});  	
        }
});

function saveLastExamSituation(){
	
}

function closeSearchResults(){
	$('#search_results').empty();
	$('#search-container').removeClass('show');
}

function showResponseMessage(success, errorMsg){
  var alert = document.createElement('div');

  if(success)
    $(alert).addClass('alert alert-success');
  else
    $(alert).addClass('alert alert-warning');

  	$(alert).html(errorMsg);
  	$('#alert').append(alert);

  setTimeout(() => {
    $('#alert').empty();
  }, (3000));
}

function initializeExamCounter(){
	$("[data-timeout]").map((e,k) => {
		var timeout = $(k).data('timeout');
		var startTime = $(k).data('starttime');
		counter(k,startTime, timeout);
	});
}

function counter(elm, start, timeout){
	
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
	
	setTimeout(()=> {counter(elm,start,timeout)},1000);
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

