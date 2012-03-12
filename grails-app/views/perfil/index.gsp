<%@ page import="com.fruiz.tankmon.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<title><g:message code="perfil.edit.label" /></title>
        <r:require module="jquery" />
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
                <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
                <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
            </sec:ifAnyGranted>
        </ul>
        </content>
		<div class="row-fluid">

				<div class="page-header">
					<h1><g:message code="perfil.edit.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
                    <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${usuario}">
                    <bootstrap:alert class="alert-error fade in">
                        <ul>
                            <g:eachError bean="${usuario}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </bootstrap:alert>
				</g:hasErrors>

                <g:form action="actualiza" id="${usuario?.id}">
					<g:hiddenField name="version" value="${usuario?.version}" />
				    <fieldset>
                        <div class="control-group ${hasErrors(bean: usuario, field: 'empresa', 'error')}">
                            <label for="empresa">
                                <g:message code="empresa.label" />
                                <span class="required-indicator">*</span>
                            </label>
                            <g:select name="empresa.id" id="empresaId" required="" value="${usuario?.empresa.id}" from="${empresas}" optionKey="id"/>
                        </div>
                        <div class="control-group ${hasErrors(bean: usuario, field: 'password', 'error')}">
                            <label for="password">
                                <g:message code="password.label" />
                                <span class="required-indicator">*</span>
                            </label>
                            <g:passwordField name="password" required="" value="${usuario?.password}"/>
                        </div>
                    </fieldset>
                    <p class="well" style="margin-top:10px;">
                        <button type="submit" class="btn btn-large btn-primary">
                            <i class="icon-ok icon-white"></i>
                            <g:message code="default.button.update.label" />
                        </button>
                        <g:link class="btn btn-large" uri="/">
                            <g:message code="default.button.cancel.label" />
                        </g:link>
                    </p>
                </g:form>
				
		</div>
        <r:script>
        $(document).ready(function(){
            $('select#empresaId').focus();
        });
        </r:script>
	</body>
</html>
