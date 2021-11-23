<%@ page language="java" contentType="text/html; charset=ISO-8859-9"
    pageEncoding="ISO-8859-9"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--===========================================================================-->
<meta http-equiv = "Content-Type" content="text/html; charset=utf-8">
<!--===========================================================================-->
<title>Questioner - Çevrimiçi sýnavlarýnýz için...</title>
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
<%@page 
	import= "javax.servlet.http.HttpSession"
	import= "java.util.ArrayList"
	import= "db.questioner.Users"
	import= "db.questioner.Exams"
	import= "db.questioner.Teachers"
	import= "db.questioner.University"
	import= "db.questioner.Faculty"
	import= "db.questioner.Department"
%>
<% 
	Users user = (Users)session.getAttribute("user");
	ArrayList<Object> universityInfo = (ArrayList<Object>)session.getAttribute("university_info");
	ArrayList<Exams> exams_old = (ArrayList<Exams>)session.getAttribute("exams_old");
	ArrayList<Teachers> teachers = (ArrayList<Teachers>)session.getAttribute("teachers");
	University uni = (University) universityInfo.get(0);
	Faculty fac = (Faculty) universityInfo.get(1);
	Department dep = (Department) universityInfo.get(2);
%>
			<div id="alert" class="fixed-top align-items-end flex-column d-none"></div>
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
           					<li><a class="dropdown-item" href="#">Sýnav Geçmiþi</a></li>
            				<li><a class="dropdown-item" href="#">Ayarlar</a></li>
            				<li><a class="dropdown-item" href="/profile">Profil</a></li>
            				<li><hr class="dropdown-divider"></li>
            				<li><a class="dropdown-item" href="/logout">Çýkýþ</a></li>
          				</ul>
        			</div>
				</div>
			</nav>
		</div>
		<div class="container-xxl d-flex">
			<div class="container-fluid p-2 col-3 bd-highlight">
				<div class="d-flex flex-column bg-white border rounded m-r-5 shadow-sm">
					<div class="p-t-20 p-b-20 text-center"><img class="img-thumbnail rounded-circle" width=150 height=150 src="<%=user.getProfileUrl() %>"/></div>
					<div class="p-b-20 text-center">
						<div class="p-b-5"><p class="fw-light fs-16"><%=user.getName() %> <%=user.getSurname() %></p></div>
						<div class="p-b-5"><i class="fs-13"><%=user.getBio() %></i><button><i class="zmdi zmdi-hc-fw zmdi-edit"></i></button></div>
					</div>
					<div class="p-b-20">
						<button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#uni-collapse" aria-expanded="false">Okul Bilgileri</button>
						<div class="collapse" id="uni-collapse">
          					<ul class="btn-toggle-nav fw-normal pb-1 small">
           	 					<li class="p-1 rounded">Okul: <i><%=uni.getName() %></i></li>
            					<li class="p-1">Fakülte: <i><%=fac.getName() %></i></li>
            					<li class="p-1">Bölüm: <i><%=dep.getName() %></i></li>
          					</ul>
        				</div>
					</div>
					<div class="p-b-20">
						<button class="btn btn-toggle align-items-center rounded" data-bs-toggle="collapse" data-bs-target="#cre-collapse" aria-expanded="true">Katýlma Tarihi</button>
						<div class="collapse show" id="cre-collapse">
          					<ul class="btn-toggle-nav fw-normal pb-1 small">
           	 					<li class="p-1 rounded"><i><%=user.getCreateTime() %></i></li>
          					</ul>
        				</div> 
					</div>	
				</div>
			</div>
			<div class="container-fluid col p-2">
				<div class="bg-white border rounded p-2 m-r-5 shadow-sm">
					<nav class="navbar navbar-expand-lg navbar-dark bg-blue" aria-label="Eighth navbar example">
    					<div class="container">
    						<p class="navbar-brand">Questioner</p>
    						<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#profileNavbar" aria-controls="profileNavbar" aria-expanded="false" aria-label="Toggle navigation">
      							<span class="navbar-toggler-icon"></span>
    						</button>
      						<div class="collapse navbar-collapse" id="profileNavbar">
        						<ul class="nav nav-tabs me-auto mb-2 mb-lg-0">
         							<li class="nav-item" role="presentation">
         								<button class="nav-link text-white active" id="exams-tab" data-bs-toggle="tab" data-bs-target="#exams" type="button" role="tab" aria-controls="exams" aria-selected="true">Sýnavlarým</button>
         							</li>
          							<li class="nav-item" role="presentation">
          								<button class="nav-link text-white" id="teachers-tab" data-bs-toggle="tab" data-bs-target="#teachers" type="button" role="tab" aria-controls="teachers" aria-selected="false">Öðretmenlerim</button>
          							</li>
        						</ul>
        						<form>
          							<input class="form-control" type="text" placeholder="Ara" aria-label="Ara">
        						</form>
      						</div>
    					</div>
  					</nav>
  					<div class="tab-content">
  						<div class="tab-pane fade p-3 active show" id="exams" aria-labelledby="exams-tab">
							<div><h3 class="site-header header-blue">Sýnavlarým</h3></div>
							<hr>
							<div class="acordion" id="examAccordion">
							<%for(int i = 0; i < exams_old.size(); ++i){ 
								Exams e = exams_old.get(i);
							%>
								<div class="accordion-item">
									<h2 class="accordion-header" id="exam_<%=e.getId() %>">
										<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#examCol_<%=e.getId() %>" aria-expanded="false" aria-controls="examCol_<%=e.getId() %>"><%=e.getHeader() %></button>
									</h2>
									<div id="examCol_<%=e.getId() %>" class="accordion-collapse collapse" aria-labelledby="exam_<%=e.getId() %>" data-bs-parent="#examAccordion">
      									<div class="accordion-body">
        									<h6 class="site-header">Sýnav Bilgileri</h6>
      										<hr />
        									<ul>
        										<li class="p-b-10"><%= e.getInformation()%></li>
        										<li class="p-b-5"><p>Soru sayýsý  : <%=e.getQuestionCount() %></p></li>
        										<li class="p-b-5"><p>Sýnav süresi : <%=e.getTime() %></p></li>
        									</ul>
        									<div class="p-t-5 p-b-5 text-end blockquote-footer">Sýnav Öðretmeni: <%=e.getTeacherName() %> <%=e.getTeacherSurname() %></div>
        									<hr/>
        									<div class="flex-sb">
        										<div class="flex-col">
        											<div class="p-b-5">Baþlama tarihi - Bitiþ tarihi</div>
        											<div class="p-b-5"><%= e.getStartDateStr() %> - <%= e.getEndDateStr() %></div>
        										</div>
        									</div>
      									</div>
    								</div>
								</div>
							<%} %>
							</div>
						</div>
						<div class="tab-pane fade p-3" id="teachers" aria-labelledby="teachers-tab">
							<div><h3 class="site-header header-blue">Öðretmenlerim</h3></div>
							<hr>
							<div class="container">
								<div class="row border rounded p-2" id="teacherContainer">
							<%for(int i = 0; i < teachers.size(); ++i){ 
								Teachers e = teachers.get(i);
							%>
								
									<a class="col-sm-2 text-center p-3 border-effect" href="/teacher/<%=e.getTeacherId()%>">
										<div class="p-b-10"><img class="img-thumbnail rounded-circle" width=100 height=100 src="<%=e.getProfileUrl() %>"/></div>
										<div class="p-b-10"><p class="text-nowrap text-center fs-6 header-blue"><%=e.getFullname()%></div>
									</a>
						<%} %>
								</div>
							</div>
						</div>
  					</div>	
					
					
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
          				<li class="nav-item mb-2"><a href="/help" class="nav-link p-0 text-muted">Yardým</a></li>
          				<li class="nav-item mb-2"><a href="/policies" class="nav-link p-0 text-muted">Koþullar</a></li>
          				<li class="nav-item mb-2"><a href="/faq" class="nav-link p-0 text-muted">Sýkça sorulan sorular</a></li>
          				<li class="nav-item mb-2"><a href="/about" class="nav-link p-0 text-muted">Hakkýmýzda</a></li>
        			</ul>
      			</div>
      			<div class="col-2">
        			<h5>Site</h5>
        			<ul class="nav flex-column">
          				<li class="nav-item mb-2"><a href="/site_map" class="nav-link p-0 text-muted">Site haritasý</a></li>
          				<li class="nav-item mb-2"><a href="/features" class="nav-link p-0 text-muted">Özellikler</a></li>
          				<li class="nav-item mb-2"><a href="/career" class="nav-link p-0 text-muted">Kariyer</a></li>
          				<li class="nav-item mb-2"><a href="/cookies" class="nav-link p-0 text-muted">Çerezler</a></li>
        			</ul>
      			</div>
      			<div class="col-4 offset-1">
        			<form>
          				<h5>Bültene abone ol</h5>
          				<p>Aylýk haberlerden haberdar olmak ve bildirimler almak için abone ol.</p>
          				<div class="d-flex w-100 gap-2">
            				<label for="newsletter1" class="visually-hidden">Email adresi</label>
            				<input id="newsletter1" type="text" class="form-control" placeholder="Email adresi">
            				<button class="btn btn-primary btn-blue" type="button">Abone</button>
          				</div>
        			</form>
      			</div>
    		</div>
    		<div class="d-flex justify-content-between py-4 my-4 border-top">
      			<p>Questioner © 2021 Company, Inc. Tüm haklarý saklýdýr.</p>
      			<ul class="list-unstyled d-flex">
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use href="/svg/twitter.svg#twitter"></use></svg></a></li>
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use xlink:href="/svg/instagram.svg#instagram"></use></svg></a></li>
        			<li class="ms-3"><a class="link-dark" href="#"><svg class="bi" width="24" height="24"><use xlink:href="/svg/facebook.svg#facebook"></use></svg></a></li>
      			</ul>
    		</div>
  		</footer>
	</div>
<!--===============================================================================================-->
<script src="/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--===============================================================================================-->
<script src="js/main.js" charset="utf-8"></script>
</body>
</html>