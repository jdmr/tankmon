<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><g:layoutTitle /> - TANKMON</title>
		<meta name="description" content="">
		<meta name="author" content="">

		<meta name="viewport" content="initial-scale = 1.0">

		<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

		<r:require modules="scaffolding"/>

		<!-- Le fav and touch icons -->
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-114x114.png')}">

		<g:layoutHead/>
		<r:layoutResources/>
	</head>

	<body>

		<nav class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					
					<a class="brand" href="${createLink(uri: '/')}">TANKMON</a>

					<div class="nav-collapse">
                        <g:pageProperty  name="page.nav" />
                        <sec:ifLoggedIn>
                        <p class="navbar-text pull-right">
                            Bienvenido <a href="${createLink(uri:'/perfil')}"><sec:username /></a>
                            <a href="${createLink(uri:'/logout')}"><i class="icon-off icon-white"></i></a>
                        </p>
                        </sec:ifLoggedIn>
					</div>

				</div>
			</div>
		</nav>

		<div class="container-fluid">
			<g:layoutBody/>

			<hr>

			<footer>
                <div class="row-fluid">
                    <div class="span8"><p>&copy; <a href='http://www.fruiz.com'>FRuiz e Hijos</a> 2012</p></div>
                    <div class="span4">
                        <p class="pull-right"><a href="${createLink(uri:'/perfil')}">${session.empresa}</a></p>
                    </div>
                </div>
			</footer>
		</div>

		<r:layoutResources/>

        <g:pageProperty name="page.scripts" />

	</body>
</html>
