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
<body>
<%@page 
	import= "jakarta.servlet.http.HttpSession"
	import= "java.util.ArrayList"
	import= "com.questioner.model.Users"
%>
<% 
	Users user = (Users)session.getAttribute("user");
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
		<div class="con-center p-t-20">
			<div class="container bg-white rounded">
				<div class="row">
					<div class="col p-1 border-bottom"><header class="site-header header-blue text-center"><h1>Sýnav Adý</h1></header></div>
				</div> <!-- UP SIDE -->
				<div class="row">
					<div class="container py-5">
						<div class="row m-1">
							<div class="col-md-3 bg-color-form rounded py-3 mx-1">
								<div class="p-2 g-1">Soru: 0/0</div>
								<div class="p-2 g-1">Kalan Süre:00.00</div>
								<div class="d-flex justify-content-center p-2 g-1"><a class="btn btn-primary" href="/exam_finish">Sýnavý bitir</a></div>
							</div> <!-- LEFT SIDE -->
							<div class="col bg-color-form rounded py-3 px-0 mx-1">
								<div class="border-bottom p-2">
									<ul>
										<li>Soru</li>
									</ul>
								</div> <!-- QUESTION CONTENT UP SIDE -->
								<div class="p-2 g-1">
									<div class="list-group">
										<button class="list-group-item list-group-item-action">A</button>
										<button class="list-group-item list-group-item-action">B</button>
										<button class="list-group-item list-group-item-action">C</button>
										<button class="list-group-item list-group-item-action">D</button>
									</div>
								</div> <!-- QUESTION CONTENT DOWN SIDE -->
								<div class="p-2 g-1 d-flex justify-content-center">
									<div class="btn-group btn-group-lg" role="group" aria-label="Basic example">
										<button type="button" class="btn btn-primary">Sonraki</button>
  										<button type="button" class="btn btn-primary">Önceki</button>
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
<script src="/js/main.js" charset="utf-8"></script>
</body>
</html>