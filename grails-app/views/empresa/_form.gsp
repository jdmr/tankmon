<%@ page import="com.fruiz.tankmon.Empresa" %>

<div class="control-group ${hasErrors(bean: empresa, field: 'nombre', 'error')}">
	<label for="nombre">
		<g:message code="nombre.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${empresa?.nombre}"/>
</div>

<div class="control-group ${hasErrors(bean: empresa, field: 'razonSocial', 'error')}">
	<label for="razonSocial">
		<g:message code="razonSocial.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="razonSocial" maxlength="128" required="" value="${empresa?.razonSocial}"/>
</div>

<div class="control-group ${hasErrors(bean: empresa, field: 'rfc', 'error')}">
	<label for="rfc">
		<g:message code="rfc.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="rfc" maxlength="13" required="" value="${empresa?.rfc}"/>
</div>

<div class="control-group ${hasErrors(bean: empresa, field: 'sitioWeb', 'error')} ">
	<label for="sitioWeb">
		<g:message code="sitioWeb.label" />
		
	</label>
	<g:textField name="sitioWeb" maxlength="128" value="${empresa?.sitioWeb}"/>
</div>

<div class="control-group ${hasErrors(bean: empresa, field: 'maximoUsuarios', 'error')}">
	<label for="maximoUsuarios">
		<g:message code="maximoUsuarios.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="maximoUsuarios" required="" value="${empresa.maximoUsuarios}" min="1" style="text-align:right;"/>
</div>
