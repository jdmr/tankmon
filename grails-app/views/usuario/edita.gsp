<%@ page import="com.fruiz.tankmon.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
        </ul>
        </content>
		<div class="row-fluid">

				<div class="page-header">
					<h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
                        <g:render template="form"/>
                    </fieldset>
                    <p class="well" style="margin-top:10px;">
                        <button type="submit" class="btn btn-large btn-primary">
                            <i class="icon-ok icon-white"></i>
                            <g:message code="default.button.update.label" />
                        </button>
                        <g:link class="btn btn-large" action="lista">
                            <g:message code="default.button.cancel.label" />
                        </g:link>
                    </p>
                </g:form>
				
		</div>
        <r:script>
        $(document).ready(function(){
            $('input#username').focus();
        });
        </r:script>
	</body>
</html>
