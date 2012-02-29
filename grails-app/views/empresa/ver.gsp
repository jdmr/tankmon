
<%@ page import="com.fruiz.tankmon.Empresa" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'empresa.label', default: 'Empresa')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${empresaInstance?.nombre}">
						<dt><g:message code="empresa.nombre.label" default="Nombre" /></dt>
						
							<dd><g:fieldValue bean="${empresaInstance}" field="nombre"/></dd>
						
					</g:if>
				
					<g:if test="${empresaInstance?.razonSocial}">
						<dt><g:message code="empresa.razonSocial.label" default="Razon Social" /></dt>
						
							<dd><g:fieldValue bean="${empresaInstance}" field="razonSocial"/></dd>
						
					</g:if>
				
					<g:if test="${empresaInstance?.rfc}">
						<dt><g:message code="empresa.rfc.label" default="Rfc" /></dt>
						
							<dd><g:fieldValue bean="${empresaInstance}" field="rfc"/></dd>
						
					</g:if>
				
					<g:if test="${empresaInstance?.sitioWeb}">
						<dt><g:message code="empresa.sitioWeb.label" default="Sitio Web" /></dt>
						
							<dd><g:fieldValue bean="${empresaInstance}" field="sitioWeb"/></dd>
						
					</g:if>
				
					<g:if test="${empresaInstance?.maximoUsuarios}">
						<dt><g:message code="empresa.maximoUsuarios.label" default="Maximo Usuarios" /></dt>
						
							<dd><g:fieldValue bean="${empresaInstance}" field="maximoUsuarios"/></dd>
						
					</g:if>
				
					<g:if test="${empresaInstance?.usuarios}">
						<dt><g:message code="empresa.usuarios.label" default="Usuarios" /></dt>
						
							<g:each in="${empresaInstance.usuarios}" var="u">
							<dd><g:link controller="usuario" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${empresaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${empresaInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
