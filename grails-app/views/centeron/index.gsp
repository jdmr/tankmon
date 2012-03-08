<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<title><g:message code="centeron.label" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
        </ul>
        </content>
		<div class="row-fluid">

				<div class="page-header">
					<h1><g:message code="centeron.label" /></h1>
				</div>

				<g:if test="${flash.message}">
                    <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
				</g:if>

                <p>
                <a href="${createLink(action: 'actualiza')}" class="btn btn-large btn-primary">Actualiza</a>
                </p>
		</div>
	</body>
</html>
