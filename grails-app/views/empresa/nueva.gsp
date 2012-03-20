<%@ page import="com.fruiz.tankmon.Empresa" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'empresa.label', default: 'Empresa')}" />
		<title><g:message code="default2.create.label" args="[entityName]" /></title>
        <r:require module="jquery" />
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
        </ul>
        </content>
		<div class="row-fluid">

				<div class="page-header">
					<h1><g:message code="default2.create.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
                    <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${empresa}">
                    <bootstrap:alert class="alert-error fade in">
                        <ul>
                            <g:eachError bean="${empresa}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </bootstrap:alert>
				</g:hasErrors>

                <g:form action="crea" enctype="multipart/form-data" >
				    <fieldset>
                        <g:render template="form"/>
                    </fieldset>
                    <p class="well" style="margin-top:10px;">
                        <button type="submit" class="btn btn-large btn-primary">
                            <i class="icon-ok icon-white"></i>
                            <g:message code="default.button.create.label" default="Create" />
                        </button>
                        <g:link class="btn btn-large" action="lista">
                            <g:message code="default.button.cancel.label" />
                        </g:link>
                    </p>
                </g:form>
				
		</div>
        <r:script>
        $(document).ready(function(){
            $('input#nombre').focus();
        });
        </r:script>
	</body>
</html>
