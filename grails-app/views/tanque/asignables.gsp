<%@ page import="com.fruiz.tankmon.Tanque" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'tanque.label', default: 'Tanque')}" />
		<title>Tanques por Asignar</title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
        </ul>
        </content>

        <div class="page-header">
            <h1>Tanques por Asignar</h1>
        </div>

        <g:form action="asignables" method="post">
            <div class="row-fluid">
                <div class="well">
                    <g:link class="btn" action="lista">
                        <i class="icon-list"></i> Lista
                    </g:link>
                    <g:link class="btn btn-primary" action="nuevo">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default.create.label" args="[entityName]" />
                    </g:link>
                    <input name="filtro" type="text" class="input-large search-query" value="${params.filtro}">
                    <button type="submit" class="btn" name="_action_asignables">
                        <i class="icon-search"></i>
                        <g:message code="default.button.search.label" default="Search" />
                    </button>
                    <button class="btn btn-warning" type="submit" name="_action_asignables" onclick="return confirm('¿Está seguro que desea asignar estos tanques a ${session.empresa}?');">
                        <i class="icon-plus icon-white"></i> Guardar Asignaciones
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
                        
                            <g:sortableColumn property="asignacion" title="${message(code: 'asignacion.label')}" />
                        
                            <g:sortableColumn property="serie" title="${message(code: 'serie.label')}" />
                        
                            <g:sortableColumn property="fechaFabricacion" title="${message(code: 'fechaFabricacion.label')}" />
                        
                            <g:sortableColumn property="capacidad" title="${message(code: 'capacidad.label')}" />
                        
                            <g:sortableColumn property="producto" title="${message(code: 'producto.label')}" />
                            
                            <g:sortableColumn property="tipo" title="${message(code: 'tipo.label')}" />
                        
                            <th><g:message code="empresa.label" /></th>

                            <th style='text-align:center;'>Asignar</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tanques}" var="tanque">
                        <tr>
                        
                            <td><g:link action="ver" id="${tanque.id}">${fieldValue(bean: tanque, field: "asignacion")}</g:link></td>
                        
                            <td>${fieldValue(bean: tanque, field: "serie")}</td>
                        
                            <td>${fieldValue(bean: tanque, field: "fechaFabricacion")}</td>
                        
                            <td>${fieldValue(bean: tanque, field: "capacidad")} ${fieldValue(bean: tanque, field: "unidades")}</td>
                        
                            <td>${fieldValue(bean: tanque, field: "producto")}</td>
                        
                            <td>${fieldValue(bean: tanque, field: "tipo")}</td>
                        
                            <td>${fieldValue(bean: tanque, field: "empresa.nombre")}</td>

                            <td style='text-align:center;'><g:checkBox name="asignaciones" value="${tanque.id}" checked="false"/></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="pagination">
                    <bootstrap:paginate total="${totalDeTanques}" params="${params}" />
                </div>

            </div>
        </g:form>
        <content tag="scripts">
        <script src="${createLink(uri:'/js/lista2.js')}" type="text/javascript" ></script>
        </content>
	</body>
</html>
