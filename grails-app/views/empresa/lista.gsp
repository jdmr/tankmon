
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

        <div class="page-header">
            <h1><g:message code="empresa.list.label" /></h1>
        </div>

        <g:form action="lista" method="post">
            <div class="row-fluid">
                <div class="well">
                    <g:link class="btn btn-primary" action="nueva">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default2.create.label" args="[entityName]" />
                    </g:link>
                    <input name="filtro" type="text" class="input-large search-query" value="${params.filtro}">
                    <button type="submit" class="btn">
                        <i class="icon-search"></i>
                        <g:message code="default.button.search.label" default="Search" />
                    </button>
                </div>
            </div>

            <g:if test="${flash.message}">
                <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
            </g:if>
            
            <div class="row-fluid">
                <table id="lista" class="table table-striped">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'nombre.label')}" />
                        
                            <g:sortableColumn property="razonSocial" title="${message(code: 'razonSocial.label')}" />
                        
                            <g:sortableColumn property="rfc" title="${message(code: 'rfc.label')}" />
                        
                            <g:sortableColumn property="sitioWeb" title="${message(code: 'sitioWeb.label')}" />
                        
                            <g:sortableColumn property="maximoUsuarios" title="${message(code: 'maximoUsuarios.label')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${empresas}" var="empresa">
                        <tr>
                        
                            <td><g:link action="ver" id="${empresa.id}">${fieldValue(bean: empresa, field: "nombre")}</g:link></td>
                        
                            <td>${fieldValue(bean: empresa, field: "razonSocial")}</td>
                        
                            <td>${fieldValue(bean: empresa, field: "rfc")}</td>
                        
                            <td>${fieldValue(bean: empresa, field: "sitioWeb")}</td>
                        
                            <td>${fieldValue(bean: empresa, field: "maximoUsuarios")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="pagination">
                    <bootstrap:paginate total="${totalDeEmpresas}" />
                </div>

            </div>
        </g:form>
        <content tag="scripts">
        <script src="${createLink(uri:'/js/lista.js')}" type="text/javascript" ></script>
        </content>
	</body>
</html>
