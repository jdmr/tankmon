<%@ page import="com.fruiz.tankmon.Tanque" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'tanque.label', default: 'Tanque')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
        </ul>
        </content>
        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        </div>

        <div class="row-fluid">
            <div class="well">
                <g:link class="btn btn-primary" action="lista">
                    <i class="icon-list icon-white"></i>
                    <g:message code="tanque.list.label" />
                </g:link>
                <g:link class="btn btn-primary" action="nuevo">
                    <i class="icon-plus icon-white"></i>
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
            <g:if test="${tanque?.asignacion}">
                <h4><g:message code="asignacion.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="asignacion"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.serie}">
                <h4><g:message code="serie.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="serie"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.fechaFabricacion}">
                <h4><g:message code="fechaFabricacion.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="fechaFabricacion"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.capacidad}">
                <h4><g:message code="capacidad.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="capacidad"/> <g:fieldValue bean="${tanque}" field="unidades"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.largo}">
                <h4><g:message code="largo.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="largo"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.ancho}">
                <h4><g:message code="ancho.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="ancho"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.profundo}">
                <h4><g:message code="profundo.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="profundo"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.diametro}">
                <h4><g:message code="diametro.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="diametro"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.producto}">
                <h4><g:message code="producto.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="producto"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.capacidadLleno}">
                <h4><g:message code="capacidadLleno.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="capacidadLleno"/> <g:fieldValue bean="${tanque}" field="unidades"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.capacidadVacio}">
                <h4><g:message code="capacidadVacio.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="capacidadVacio"/> <g:fieldValue bean="${tanque}" field="unidades"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.tipo}">
                <h4><g:message code="tipo.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="tipo"/></h3>
                <p></p>
            </g:if>
        </div>
        
        <div class="row-fluid">
            <g:if test="${tanque?.empresa}">
                <h4><g:message code="empresa.label" /></h4>
                <h3><g:fieldValue bean="${tanque}" field="empresa.nombre"/></h3>
                <p></p>
            </g:if>
        </div>

        <div class="row-fluid">
            <g:form>
                <g:hiddenField name="id" value="${tanque?.id}" />
                <div class="well">
                    <g:link class="btn btn-large" action="edita" id="${tanque?.id}">
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
