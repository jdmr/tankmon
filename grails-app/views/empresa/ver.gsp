<%@ page import="com.fruiz.tankmon.Empresa" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'empresa.label', default: 'Empresa')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
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
        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        </div>

        <div class="row-fluid">
            <div class="well">
                <g:link class="btn btn-primary" action="lista">
                    <i class="icon-list icon-white"></i>
                    <g:message code="empresa.list.label" />
                </g:link>
                <g:link class="btn btn-primary" action="nueva">
                    <i class="icon-plus icon-white"></i>
                    <g:message code="default2.create.label" args="[entityName]" />
                </g:link>
            </div>
        </div>

        <div class="row-fluid">
            <g:if test="${flash.message}">
                <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
            </g:if>
        </div>

        <div class="row-fluid">
            <div class="span8">
                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="nombre.label" /></h4>
                        <h3><g:fieldValue bean="${empresa}" field="nombre"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="razonSocial.label" /></h4>
                        <h3><g:fieldValue bean="${empresa}" field="razonSocial"/></h3>
                        <p></p>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="rfc.label" /></h4>
                        <h3><g:fieldValue bean="${empresa}" field="rfc"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="maximoUsuarios.label" /></h4>
                        <h3><g:fieldValue bean="${empresa}" field="maximoUsuarios"/></h3>
                        <p></p>
                    </div>
                </div>
            </div>
            <div class="span4">
                <p><img src="${createLink(action:'imagen',id:empresa.id)}" /></p>
            </div>
        </div>

        <div class="row-fluid">
            <g:form>
                <g:hiddenField name="id" value="${empresa?.id}" />
                <div class="well">
                    <g:link class="btn btn-large" action="edita" id="${empresa?.id}">
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
