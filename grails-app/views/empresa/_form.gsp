<%@ page import="com.fruiz.tankmon.Empresa" %>



<div class="fieldcontain ${hasErrors(bean: empresa, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="empresa.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${empresa?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresa, field: 'razonSocial', 'error')} required">
	<label for="razonSocial">
		<g:message code="empresa.razonSocial.label" default="Razon Social" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="razonSocial" maxlength="128" required="" value="${empresa?.razonSocial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresa, field: 'rfc', 'error')} required">
	<label for="rfc">
		<g:message code="empresa.rfc.label" default="Rfc" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="rfc" maxlength="13" required="" value="${empresa?.rfc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresa, field: 'sitioWeb', 'error')} ">
	<label for="sitioWeb">
		<g:message code="empresa.sitioWeb.label" default="Sitio Web" />
		
	</label>
	<g:textField name="sitioWeb" maxlength="128" value="${empresa?.sitioWeb}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresa, field: 'maximoUsuarios', 'error')} required">
	<label for="maximoUsuarios">
		<g:message code="empresa.maximoUsuarios.label" default="Maximo Usuarios" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="maximoUsuarios" required="" value="${empresa.maximoUsuarios}" min="1" style="text-align:right;"/>
</div>
