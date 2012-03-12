<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap"/>
		<title>Inicio</title>
	</head>

	<body>
        <content tag="nav">
        <ul class="nav">
            <li class="active"><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
                <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
                <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
            </sec:ifAnyGranted>
        </ul>
        </content>
		<div class="row-fluid">
			<section id="main" class="span12">

                <g:if test="${flash.message}">
                    <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
                </g:if>

				<div class="hero-unit">
					<h1>Bienvenidos a TANKMON</h1>

					<p>Monitoreo de tanques... lorem ipsum etc, etc.</p>
					
				</div>
					
			</section>
		</div>
	</body>
</html>
