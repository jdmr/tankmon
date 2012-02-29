
<%@ page import="com.fruiz.tankmon.Empresa" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'empresa.label', default: 'Empresa')}" />
		<title><g:message code="empresa.list.label" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
        </ul>
        </content>
        <g:form action="lista" method="post">
            <div class="row-fluid well">
                <g:link class="btn btn-primary" action="nueva">
                    <i class="icon-plus icon-white"></i>
                    <g:message code="default.create.label" args="[entityName]" />
                </g:link>
                <input name="filtro" type="text" class="input-large search-query" value="${params.filtro}">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i>
                    <g:message code="default.button.search.label" default="Search" />
                </button>
            </div>
            <div class="row-fluid">
                
                <div class="span3">
                    <div class="well">
                        <ul class="nav nav-list">
                            <li class="nav-header">${entityName}</li>
                            <li class="active">
                                <g:link class="list" action="list">
                                    <i class="icon-list icon-white"></i>
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
                        <h1><g:message code="empresa.list.label" /></h1>
                    </div>

                    <g:if test="${flash.message}">
                    <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
                    </g:if>
                    
                    <table class="table table-striped">
                        <thead>
                            <tr>
                            
                                <g:sortableColumn property="nombre" title="${message(code: 'empresa.nombre.label', default: 'Nombre')}" />
                            
                                <g:sortableColumn property="razonSocial" title="${message(code: 'empresa.razonSocial.label', default: 'Razon Social')}" />
                            
                                <g:sortableColumn property="rfc" title="${message(code: 'empresa.rfc.label', default: 'Rfc')}" />
                            
                                <g:sortableColumn property="sitioWeb" title="${message(code: 'empresa.sitioWeb.label', default: 'Sitio Web')}" />
                            
                                <g:sortableColumn property="maximoUsuarios" title="${message(code: 'empresa.maximoUsuarios.label', default: 'Maximo Usuarios')}" />
                            
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${empresaInstanceList}" var="empresaInstance">
                            <tr>
                            
                                <td>${fieldValue(bean: empresaInstance, field: "nombre")}</td>
                            
                                <td>${fieldValue(bean: empresaInstance, field: "razonSocial")}</td>
                            
                                <td>${fieldValue(bean: empresaInstance, field: "rfc")}</td>
                            
                                <td>${fieldValue(bean: empresaInstance, field: "sitioWeb")}</td>
                            
                                <td>${fieldValue(bean: empresaInstance, field: "maximoUsuarios")}</td>
                            
                                <td class="link">
                                    <g:link action="show" id="${empresaInstance.id}" class="btn btn-small">Show &raquo;</g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                    <div class="pagination">
                        <bootstrap:paginate total="${empresaInstanceTotal}" />
                    </div>
                </div>

            </div>
        </g:form>
	</body>
</html>
