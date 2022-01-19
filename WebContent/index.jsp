<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page import= "java.util.ArrayList" %>
<%@page import="com.questioner.dictionary.EExamStatus"%>
<%@page	import= "com.questioner.model.Users"%>
<%@page	import= "com.questioner.model.StudentExams" %>
	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--===========================================================================-->
<meta http-equiv = "Content-Type" content="text/html; charset=utf-8">
<!--===========================================================================-->
<title>Questioner - Çevrimiçi sınavlarınız için...</title>
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/vendor/bootstrap/css/bootstrap.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/util.css">
	<link rel="stylesheet" type="text/css" href="/css/main.css">
<!--===============================================================================================-->
</head>
<body>

<% 
	Users user = (Users)session.getAttribute("user"); 
	ArrayList<StudentExams> exams = (ArrayList<StudentExams>)session.getAttribute("exams");
%>
	<div id="alert" class="fixed-bottom" style="left:unset;">
	<%if(request.getAttribute("response") != null) {%>
		<%if((int)request.getAttribute("errorCode") >= 1) {%>
			<div class="alert alert-danger"><%= request.getAttribute("errorMsg") %></div>
		<%}else{ %>
			<div class="alert alert-success"><%= request.getAttribute("errorMsg") %></div>
		<%} %>
	<%}%>
	</div>
	<div class="limiter">
	<%if(user != null){ %>
		
		<div class="nav-con">
			<nav class="nav-i flex-l-m">
				<div class="flex-5">
					<a class="site-header fs-32" href="/">Questioner</a>
				</div>
				<div class="flex-col-r flex-1 m-r-100">
					<div class="dropdown text-end">
          				<a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="profileMenu" data-bs-toggle="dropdown" aria-expanded="false">
            				<img src="<%=user.getProfileUrl() %>" alt="mdo" width="56" height="56" class="rounded-circle">
          				</a>
          				<ul class="dropdown-menu text-small" aria-labelledby="profileMenu">
          					<li><p class="dropdown-item"><%=user.getName() %> <%=user.getSurname() %></p></li>
          					<li><hr class="dropdown-divider"></li>
           					<li><a class="dropdown-item" href="#">Sınav Geçmişi</a></li>
            				<li><a class="dropdown-item" href="#">Ayarlar</a></li>
            				<li><a class="dropdown-item" href="/profile">Profil</a></li>
            				<li><hr class="dropdown-divider"></li>
            				<li><a class="dropdown-item" href="/logout">Çıkış</a></li>
          				</ul>
        			</div>
				</div>
			</nav>
		</div>
	<%
	} 
	if(user != null && user.getRole().equals("stu") && !(boolean)session.getAttribute("has_exam")){
	%>
		<div class="con-center">
			<div class="p-t-100 p-b-200">
				<div class="flex-col-c flex-2 m-r-20">
					<div class="txt-l text-left m-b-10">Herhangi bir sınava kayıt olmadın. Şimdi kodunu gir ve sınava başla.</div>
					<form class="lg-form" action="/find_exam" method="GET">
						<div class="flex-col">
							<input class="inp" name="exam_uuid" type="text" placeholder="Sınav kodunu girin">
						</div>
						<div class="flex-col">
							<button class="btn btn-b btn-blue">Ara</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	<%}else if(user != null && user.getRole().equals("stu") && (boolean)session.getAttribute("has_exam")){%>
		<div class="con-center p-t-20">
			<div class="container shadow-sm border-0 bg-white rounded p-b-20">
				<div class="col p-t-20 p-b-10">
					<h3 class="site-header header-blue">SINAVLARIN</h3>
				</div>
				<hr/>
				<div class="accordion" id="examAcordion">
				<%
					for(int i = 0; i < exams.size(); ++i){ 
						StudentExams e = exams.get(i);
				%>
					<div class="accordion-item">
						<h2 class="accordion-header" id="exam_<%=e.getId() %>">
								<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#examCol_<%=e.getId() %>" aria-expanded="false" aria-controls="examCol_<%=e.getId() %>"><%=e.getExam().getHeader() %></button>
						</h2>
						<div id="examCol_<%=e.getId() %>" class="accordion-collapse collapse" aria-labelledby="exam_<%=e.getId() %>" data-bs-parent="#examAcordion">
      						<div class="accordion-body">
        						<h6 class="site-header">Sınav Bilgileri</h6>
      							<hr />
        						<ul>
        							<li class="p-b-10"><%= e.getExam().getInformation()%></li>
        							<li class="p-b-5"><p>Soru sayısı  : <%=e.getExam().getQuestionCount() %></p></li>
        							<li class="p-b-5"><p>Sınav süresi : <%=e.getExam().getTime() %></p></li>
        						</ul>
        						<hr/>
        						<div class="flex-sb">
        							<div class="flex-col">
        								<div class="p-b-5">Başlama tarihi - Bitiş tarihi</div>
        								<div class="p-b-5"><%= e.getExam().getStartDateStr() %> - <%= e.getExam().getEndDateStr() %></div>
        							</div>
        							<div class="flex-col-r">
        							<%if(e.getExam().isInterval()) {
        								if(e.getStatus() == EExamStatus.STARTED){%>
        									<a class="btn btn-primary" href="/exam?id=<%=e.getExam().getId()%>">Sınava devam et</a>
        								<%} 
        								else{%>
        									<a class="btn btn-primary" href="/exam?id=<%=e.getExam().getId()%>">Sınava başla</a>
        								<%} %>
        							<%}else{ %>
        									<div class="p-b-5">Sınavın başlamasına:</div>
        									<div class="p-b-5" data-id="<%=e.getId() %>"  data-timeout = "<%=e.getExam().getEndDate()%>" data-startTime = "<%=e.getExam().getStartDate()%>"></div>
        							<%} %>
        							</div>
        						</div>
      						</div>
    					</div>
					</div>
				<%}%>
				</div>
			</div>
		</div>
	<%
		}
		else{
	%>
		<div class="con-center">
			<div class="flex-row p-t-100 p-b-200">
				<div class="flex-col-l-m flex-2 m-l-60 m-r-20">
					<div class="site-header p-t-50 p-b-20 zoomIn animated"><h1>Questioner</h1></div>
					<div class="txt-l txt-w-l lh-2-0 text-left fadeInDown animated">Questioner çevrimiçi sınav hizmeti sunar. Şimdi kayıt ol sınavlar oluştur veya öğrenci olarak sınava gir.</div>
				</div>
				<div class="flex-1 m-r-20 p-t-50">
					<div class="lg-form-c">
						<div class="form-pad">
							<form class="p-t-20 lg-form" method="POST" action="/login">
								<div class="form-i-con">
									<input class="inp" name="email" type="email" placeholder="E-posta">
								</div>
								<div class="form-i-con">
									<input class="inp" name="password" type="password" placeholder="Şifre">
								</div>
								<div class="form-i-con">
									<button class="btn btn-b btn-blue" type="submit">Giriş Yap</button>
								</div>
								<div class="form-i-con">
									<a class="" href="/forget_password.jsp">Şifreni mi unuttun?</a>
								</div>
							</form>
						</div>
						<hr/>
						<div class="form-marg flex-c">
							<button id="signUpForm" class="btn btn-b btn-green">Yeni Hesap Oluştur</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	<%}%>
	</div>
	<div class="container">
  		<footer class="py-5">
    		<div class="row">
      			<div class="col-2">
        			<h5>Questioner</h5>
        			<ul class="nav flex-column">
          				<li class="nav-item mb-2"><a href="/" class="nav-link p-0 text-muted">Ana sayfa</a></li>
          				<li class="nav-item mb-2"><a href="/help" class="nav-link p-0 text-muted">Yardım</a></li>
          				<li class="nav-item mb-2"><a href="/policies" class="nav-link p-0 text-muted">Koşullar</a></li>
          				<li class="nav-item mb-2"><a href="/faq" class="nav-link p-0 text-muted">Sıkça sorulan sorular</a></li>
          				<li class="nav-item mb-2"><a href="/about" class="nav-link p-0 text-muted">Hakkımızda</a></li>
        			</ul>
      			</div>
      			<div class="col-2">
        			<h5>Site</h5>
        			<ul class="nav flex-column">
          				<li class="nav-item mb-2"><a href="/site_map" class="nav-link p-0 text-muted">Site haritası</a></li>
          				<li class="nav-item mb-2"><a href="/features" class="nav-link p-0 text-muted">Özellikler</a></li>
          				<li class="nav-item mb-2"><a href="/career" class="nav-link p-0 text-muted">Kariyer</a></li>
          				<li class="nav-item mb-2"><a href="/cookies" class="nav-link p-0 text-muted">Çerezler</a></li>
        			</ul>
      			</div>
      			<div class="col-4 offset-1">
        			<form>
          				<h5>Bültene abone ol</h5>
          				<p>Aylık haberlerden haberdar olmak ve bildirimler almak için abone ol.</p>
          				<div class="d-flex w-100 gap-2">
            				<label for="newsletter1" class="visually-hidden">Email adresi</label>
            				<input id="newsletter1" type="text" class="form-control" placeholder="Email adresi">
            				<button class="btn btn-primary btn-blue" type="button">Abone</button>
          				</div>
        			</form>
      			</div>
    		</div>
    		<div class="d-flex justify-content-between py-4 my-4 border-top">
      			<p>Questioner © 2021 Company, Inc. Tüm hakları saklıdır.</p>
      			<ul class="list-unstyled d-flex">
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use href="/svg/twitter.svg#twitter"></use></svg></a></li>
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use xlink:href="/svg/instagram.svg#instagram"></use></svg></a></li>
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use xlink:href="/svg/facebook.svg#facebook"></use></svg></a></li>
      			</ul>
    		</div>
  		</footer>
	</div>
	<%if(user == null){%>
	<div class="fix-full">
		<div class="fix-bg-blur"></div>
		<div class="fix-w-form">
			<div class="reg-form zoomOut animated">
				<div class="abs-bg-blur">
					<div class="spinner-grow m-5" role="status" aria-hidden="true">
  						<span class="visually-hidden">Loading...</span>
					</div>
				</div>
				<div class="reg-close">
					<button id="closeSignUpForm" class="btn">X</button>
				</div>
				<div class="form-h">
					<h1 class="p-t-20 p-l-10 site-header">Kaydol</h1>
				</div>
				<hr/>
				<form class="p-t-20 p-b-20 lg-form" id="registerForm" method="POST" action="">
					<div class="form-i-con">
						<input class="inp" name="name" type="text" placeholder="Adın" required>
					</div>
					<div class="form-i-con">
						<input class="inp" name="surname" type="text" placeholder="Soyadın" required>
					</div>
					<div class="form-i-con">
						<input class="inp" name="email" type="email" placeholder="E-posta" required>
					</div>
					<div class="form-i-con">
						<input class="inp" name="password" type="password" placeholder="Şifre" required>
					</div>
					<div class="form-i-con flex-col">
						<select class="inp" name="type">
							<option value="stu" selected>Öğrenci</option>
							<option value="tea">Öğretmen</option>
						</select>
					</div>
					<div class="form-i-con flex-col">
						<div class="p-b-5 p-t-5">Üniversite</div>
						<select class="inp" name="university">
							<option value="none" selected>Seçiniz</option>
						</select>
					</div>
					<div class="form-i-con flex-col">
						<div class="p-b-5 p-t-5">Fakülte</div>
						<select class="inp" name="faculty">
							<option value="none" selected>Seçiniz</option>
						</select>
					</div>
					<div class="form-i-con flex-col">
						<div class="p-b-5 p-t-5">Bölüm</div>
						<select class="inp" name="department">
							<option value="none" selected>Seçiniz</option>
						</select>
					</div>
					<div class="form-i-con">
						 <input class="form-check-input mt-0" type="checkbox" name="check" required>
						 <div class="m-l-10">Sözleşmeyi kabul ediyorum.</div>
					</div>
					<div class="form-i-con">
						<button class="btn btn-b btn-blue" id="reqisterSubmit" type="submit">Tamamla</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%} %>
<!--===============================================================================================-->
<script src="/vendor/jquery/jquery-3.6.0.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--===============================================================================================-->
<script src="js/main.js" charset="utf-8"></script>
</body>
</html>