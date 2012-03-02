<%@ page import="com.fruiz.tankmon.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
        </ul>
        </content>
        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        </div>

        <div class="row-fluid">
            <div class="well">
                <g:link class="btn btn-primary" action="lista">
                    <i class="icon-list icon-white"></i>
                    <g:message code="usuario.list.label" />
                </g:link>
                <g:link class="btn btn-primary" action="nuevo">
                    <i class="icon-user icon-white"></i>
                    <g:message code="default.create.label" args="[entityName]" />
                </g:link>
            </div>
        </div>

        <div class="row-fluid">
            <g:if test="${flash.message}">
                <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:if test="${usuario?.username}">
                <h4><g:message code="username.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="username"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${usuario?.nombre}">
                <h4><g:message code="nombre.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="nombre"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${usuario?.apellido}">
                <h4><g:message code="apellido.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="apellido"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:if test="${usuario?.puesto}">
                <h4><g:message code="puesto.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="puesto"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:if test="${usuario?.telefono}">
                <h4><g:message code="telefono.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="telefono"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:if test="${usuario?.correo}">
                <h4><g:message code="correo.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="correo"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:if test="${usuario?.empresa}">
                <h4><g:message code="empresa.label" /></h4>
                <h3><g:fieldValue bean="${usuario}" field="empresa.nombre"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:form>
                <g:hiddenField name="id" value="${usuario?.id}" />
                <div class="well">
                    <g:link class="btn btn-large" action="edita" id="${usuario?.id}">
                        <i class="icon-pencil"></i>
                        <g:message code="default.button.edit.label" default="Edit" />
                    </g:link>
                    <button class="btn btn-large btn-danger" type="submit" name="_action_elimina" onclick="return confirm('<g:message code="default.button.delete.confirm.message" />');">
                        <i class="icon-trash icon-white"></i>
                        <g:message code="default.button.delete.label" default="Delete" />
                    </button>
                </div>
            </g:form>
        </div>
	</body>
</html>
