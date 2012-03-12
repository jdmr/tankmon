<%@ page import="com.fruiz.tankmon.Tanque" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'tanque.label', default: 'Tanque')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <bluff:resources />
	</head>
	<body>
        <content tag="nav">
        <ul class="nav">
            <li><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li class="active"><a href="${createLink(uri: '/tanque')}"><g:message code='tanque.list.label' /></a></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
                <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
                <li><a href="${createLink(uri: '/centeron')}"><g:message code='centeron.label' /></a></li>
            </sec:ifAnyGranted>
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
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <g:link class="btn btn-primary" action="nuevo">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default.create.label" args="[entityName]" />
                    </g:link>
                </sec:ifAnyGranted>
            </div>
        </div>

        <div class="row-fluid">
            <g:if test="${flash.message}">
                <bootstrap:alert class="alert-info fade in">${flash.message}</bootstrap:alert>
            </g:if>
        </div>

        <div class="row-fluid" class="span12">
            <div class="span8">
                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="asignacion.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="asignacion"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="nombre.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="nombre"/></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="fechaFabricacion.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="fechaFabricacion"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="capacidad.label" /></h4>
                        <h3><g:formatNumber number="${tanque.capacidad}" minIntegerDigits="1" maxFractionDigits="3" locale="en" /> <g:fieldValue bean="${tanque}" field="unidades"/></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="largo.label" /></h4>
                        <h3><g:formatNumber number="${tanque.largo}" minIntegerDigits="1" maxFractionDigits="3" locale="en" /></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="ancho.label" /></h4>
                        <h3><g:formatNumber number="${tanque.ancho}" minIntegerDigits="1" maxFractionDigits="3" locale="en" /></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="profundo.label" /></h4>
                        <h3><g:formatNumber number="${tanque.profundo}" minIntegerDigits="1" maxFractionDigits="3" locale="en" /></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="diametro.label" /></h4>
                        <h3><g:formatNumber number="${tanque.diametro}" minIntegerDigits="1" maxFractionDigits="3" locale="en" /></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="producto.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="producto"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="tipo.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="tipo"/></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="capacidadLleno.label" /></h4>
                        <h3><g:formatNumber number="${tanque.capacidadLleno}" locale="en" type="percent" /></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="capacidadVacio.label" /></h4>
                        <h3><g:formatNumber number="${tanque.capacidadVacio}" locale="en" type="percent" /></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="latitud.label" /></h4>
                        <h3><g:formatNumber number="${tanque.latitud}" minIntegerDigits="1" maxFractionDigits="10" locale="en" /></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="longitud.label" /></h4>
                        <h3><g:formatNumber number="${tanque.longitud}" minIntegerDigits="1" maxFractionDigits="10" locale="en" /></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="empresa.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="empresa.nombre"/></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="serie.label" /></h4>
                        <h3><g:fieldValue bean="${tanque}" field="serie"/></h3>
                        <p></p>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span6">
                        <h4><g:message code="precaucion.label" /></h4>
                        <h3><g:formatNumber number="${tanque.precaucion}" locale="en" type="percent" /></h3>
                        <p></p>
                    </div>
                    <div class="span6">
                        <h4><g:message code="alerta.label" /></h4>
                        <h3><g:formatNumber number="${tanque.alerta}" locale="en" type="percent" /></h3>
                        <p></p>
                    </div>
                </div>

            </div>
            <div class="span4">
                <div class="row-fluid">
                    <p><bluff:line id="line" data="${data}" labels="${labels}" theme="theme_pastel" tooltips="true" options="[line_width: 2]"/></p>
                </div>
                <div class="row-fluid">
                    <div id="map_canvas" style="width: 250px; height: 250px;"></div>
                </div>
            </div>
        </div>
        
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div class="row-fluid">
                <g:form>
                    <g:hiddenField name="id" value="${tanque?.id}" />
                    <div class="well">
                        <g:link class="btn btn-large" action="edita" id="${tanque?.id}">
                            <i class="icon-pencil"></i>
                            <g:message code="default.button.edit.label" default="Edit" />
                        </g:link>
                        <button class="btn btn-large btn-warning" type="submit" name="_action_enviarEstatus" onclick="return confirm('¿Seguro que desea enviar el estatus de este tanque?');">
                            <i class="icon-envelope icon-white"></i>
                            Enviar Estatus
                        </button>
                        <button class="btn btn-large btn-warning" type="submit" name="_action_desasigna" onclick="return confirm('¿Seguro que desea desasignar este tanque?');">
                            <i class="icon-remove icon-white"></i>
                            Desasigna
                        </button>
                        <button class="btn btn-large btn-danger" type="submit" name="_action_elimina" onclick="return confirm('<g:message code="default.button.delete.confirm.message" />');">
                            <i class="icon-trash icon-white"></i>
                            <g:message code="default.button.delete.label" default="Delete" />
                        </button>
                    </div>
                </g:form>
            </div>
        </sec:ifAnyGranted>
        <content tag="scripts">
            <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript" ></script>
            <script src="${createLink(uri: '/js/jquery.ui.map.full.min.js')}" type="text/javascript" ></script>
        </content>
        <r:script>
        $(function() {
                // Also works with: var yourStartLatLng = '59.3426606750, 18.0736160278';
                // var latlng = new google.maps.LatLng(37.4419, -122.1419);
                var latlng = new google.maps.LatLng(${tanque.latitud}, ${tanque.longitud});
                $('#map_canvas').gmap({'center': latlng, 'zoom': 13, 'mapTypeId': google.maps.MapTypeId.ROADMAP});
                $('#map_canvas').gmap('addMarker', { 'position': latlng, 'title': '${tanque.nombre}' });
        });
        </r:script>
	</body>
</html>
