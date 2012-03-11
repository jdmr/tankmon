<%@ page import="com.fruiz.tankmon.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title><g:message code="usuario.list.label" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
        </ul>
        </content>

        <div class="page-header">
            <h1><g:message code="usuario.list.label" /></h1>
        </div>

        <g:form action="lista" method="post">
            <div class="row-fluid">
                <div class="well">
                    <g:link class="btn btn-primary" action="nuevo">
                        <i class="icon-user icon-white"></i>
                        <g:message code="default.create.label" args="[entityName]" />
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
                        
                            <g:sortableColumn property="username" title="${message(code: 'username.label')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'nombre.label')}" />
                        
                            <g:sortableColumn property="apellido" title="${message(code: 'apellido.label')}" />
                        
                            <g:sortableColumn property="puesto" title="${message(code: 'puesto.label')}" />
                        
                            <g:sortableColumn property="telefono" title="${message(code: 'telefono.label')}" />
                        
                            <g:sortableColumn property="correo" title="${message(code: 'correo.label')}" />

                            <th><g:message code="empresa.label" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usuarios}" var="usuario">
                        <tr>
                        
                            <td><g:link action="ver" id="${usuario.id}">${fieldValue(bean: usuario, field: "username")}</g:link></td>
                        
                            <td>${fieldValue(bean: usuario, field: "nombre")}</td>
                        
                            <td>${fieldValue(bean: usuario, field: "apellido")}</td>
                        
                            <td>${fieldValue(bean: usuario, field: "puesto")}</td>
                        
                            <td>${fieldValue(bean: usuario, field: "telefono")}</td>
                        
                            <td>${fieldValue(bean: usuario, field: "correo")}</td>
                        
                            <td>${fieldValue(bean: usuario, field: "empresa.nombre")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="pagination">
                    <bootstrap:paginate total="${totalDeUsuarios}" />
                </div>

            </div>
        </g:form>
        <content tag="scripts">
        <script src="${createLink(uri:'/js/lista.js')}" type="text/javascript" ></script>
        </content>
	</body>
</html>
