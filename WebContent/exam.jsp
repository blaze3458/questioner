<%@page import="jakarta.servlet.http.HttpSession" %>
<%@page	import= "com.google.gson.JsonObject" %>
<%@page	import= "com.google.gson.JsonParser" %>
<%@page import="com.google.gson.JsonArray"%>

<%@page import= "java.util.ArrayList" %>
<%@page import="com.questioner.dictionary.EExamStatus"%>
<%@page	import= "com.questioner.model.Users"%>
<%@page	import= "com.questioner.model.Exams" %>
<%@page	import= "com.questioner.model.ExamQuestions" %>
	
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
<body>
<% 
	int lastQuestNumber = (int)session.getAttribute("last_question_number");
	long examFinishTime = (long)session.getAttribute("exam_finish_time");

	Users user = (Users)session.getAttribute("user");
	Exams exam = (Exams)session.getAttribute("exam");
	ArrayList<ExamQuestions> questions = (ArrayList<ExamQuestions>)session.getAttribute("questions");
	ExamQuestions quest = questions.get(lastQuestNumber-1);
	JsonObject jsonObject = JsonParser.parseString(quest.getAnswers()).getAsJsonObject();
	JsonArray answerArray = jsonObject.get("answers").getAsJsonArray();
%>

	<div id="alert" class="fixed-bottom" style="left:unset;"></div>
	<div class="limiter">
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
		<div class="con-center p-t-20">
			<div class="container bg-white rounded">
				<div class="row">
					<div class="col p-1 border-bottom"><header class="site-header header-blue text-center"><h1><%= exam.getHeader()%></h1></header></div>
				</div> <!-- UP SIDE -->
				<div class="row">
					<div class="container py-5">
						<div class="row m-1">
							<div class="col-md-3 bg-color-form rounded py-3 mx-1">
								<div id="question_row" class="p-2 g-1">Soru: <%=lastQuestNumber %>/<%=exam.getQuestionCount() %></div>
								<ul>
									<li id="question_point" class="p-2 g-1">Puan: <%=quest.getPoint() %></li>
									<li class="p-2 g-1">Kalan Süre:<span id="exam-remain-time" data-timeout="0" data-starttime="<%=examFinishTime%>">00.00</span></li>
								</ul>
								
								<div class="d-flex justify-content-center p-2 g-1"><a class="btn btn-primary" href="/exam_finish">Sınavı bitir</a></div>
							</div> <!-- LEFT SIDE -->
							<div class="col bg-color-form rounded py-3 px-0 mx-1">
								<div class="border-bottom py-3">
									<ul>
										<li id="exam-question"><%=quest.getQuestion() %></li>
									</ul>
								</div> <!-- QUESTION CONTENT UP SIDE -->
								<div class="p-2 g-1">
									<div id="exam-answers" class="list-group">
										<%for(int i = 0; i < answerArray.size(); ++i) {%>				
										<button class="py-3 list-group-item list-group-item-action btn-answer" data-key="<%=i %>" data-value="<%=answerArray.get(i).getAsString()%>"><%=answerArray.get(i).getAsString() %></button>
										<%} %>
									</div>
								</div> <!-- QUESTION CONTENT DOWN SIDE -->
								<div class="p-2 g-1 d-flex justify-content-center">
									<div class="btn-group btn-group-lg" role="group" aria-label="Basic example">
										<button id="next_question" type="button" class="btn btn-primary">Sonraki</button>
  										<button id="prev_question" type="button" class="btn btn-primary">Önceki</button>
									</div>
								</div>
							</div> <!-- RIGHT SIDE -->
						</div>
					</div> <!-- DOWN SIDE -->
				</div>
			</div>
		</div>
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
<!--===============================================================================================-->
<script src="/vendor/jquery/jquery-3.6.0.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--===============================================================================================-->
<script src="/js/main.js" charset="utf-8"></script>
</body>
</html>