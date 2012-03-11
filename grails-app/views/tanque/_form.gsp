<%@ page import="com.fruiz.tankmon.Tanque" %>

<div class="control-group ${hasErrors(bean: empresa, field: 'nombre', 'error')}">
	<label for="nombre">
		<g:message code="nombre.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${tanque?.nombre}"/>
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'asignacion', 'error')}">
	<label for="asignacion">
		<g:message code="asignacion.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="asignacion" required="" value="${tanque?.asignacion}"/>
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'serie', 'error')}">
	<label for="serie">
		<g:message code="serie.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="serie" maxlength="64" required="" value="${tanque?.serie}"/>
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'fechaFabricacion', 'error')}">
	<label for="fechaFabricacion">
		<g:message code="fechaFabricacion.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFabricacion" value="${tanque?.fechaFabricacion}" precision="day" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'unidades', 'error')}">
	<label for="unidades">
		<g:message code="unidades.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="unidades" maxlength="64" required="" value="${tanque?.unidades}"/>
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'capacidad', 'error')}">
	<label for="capacidad">
		<g:message code="capacidad.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="capacidad" value="${tanque?.capacidad}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'largo', 'error')}">
	<label for="largo">
		<g:message code="largo.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="largo" value="${tanque?.largo}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'ancho', 'error')}">
	<label for="ancho">
		<g:message code="ancho.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ancho" value="${tanque?.ancho}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'profundo', 'error')}">
	<label for="profundo">
		<g:message code="profundo.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="profundo" value="${tanque?.profundo}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'diametro', 'error')}">
	<label for="diametro">
		<g:message code="diametro.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="diametro" value="${tanque?.diametro}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'producto', 'error')}">
	<label for="producto">
		<g:message code="producto.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="producto" maxlength="64" required="" value="${tanque?.producto}" required="" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'capacidadLleno', 'error')}">
	<label for="capacidadLleno">
		<g:message code="capacidadLleno.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="capacidadLleno" value="${tanque?.capacidadLleno}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'capacidadVacio', 'error')}">
	<label for="capacidadVacio">
		<g:message code="capacidadVacio.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="capacidadVacio" value="${tanque?.capacidadVacio}" type="number" min="0" step="0.01" required="" style="text-align: right;" />
</div>

<div class="control-group ${hasErrors(bean: tanque, field: 'tipo', 'error')}">
	<label for="tipo">
		<g:message code="tipo.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipo" maxlength="64" required="" value="${tanque?.tipo}" required="" />
</div>

