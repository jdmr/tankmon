<%@ page import="com.fruiz.tankmon.Usuario" %>

<div class="control-group ${hasErrors(bean: usuario, field: 'username', 'error')}">
	<label for="username">
		<g:message code="username.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${usuario?.username}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'password', 'error')}">
	<label for="password">
		<g:message code="password.label" />
	</label>
	<g:passwordField name="password" value="${usuario?.password}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'nombre', 'error')}">
	<label for="nombre">
		<g:message code="nombre.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" maxlength="64" required="" value="${usuario?.nombre}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'apellido', 'error')}">
	<label for="apellido">
		<g:message code="apellido.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apellido" maxlength="64" required="" value="${usuario?.apellido}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'puesto', 'error')}">
	<label for="puesto">
		<g:message code="puesto.label" />
	</label>
	<g:textField name="puesto" maxlength="64" value="${usuario?.puesto}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'telefono', 'error')}">
	<label for="telefono">
		<g:message code="telefono.label" />
	</label>
	<g:textField name="telefono" maxlength="30" value="${usuario?.telefono}"/>
</div>

<div class="control-group ${hasErrors(bean: usuario, field: 'correo', 'error')} ">
	<label for="correo">
		<g:message code="correo.label" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="correo" maxlength="128" value="${usuario?.correo}" type="email" />
</div>

<g:if test="${roles}">
    <div class="control-group ${hasErrors(bean: usuario, field: 'authorities', 'error')} ">
        <g:set var="contador" value="${1}" />
        <g:each var="entry" in="${roles}">
            <label for="${entry.key.authority}">
                <g:if test="${contador++ == 1}">
                    <g:message code="usuario.authorities.label" default="Authorities" />
                </g:if>
            </label>
            <g:checkBox name="${entry.key.authority}" value="${entry.value}"/> <g:message code="${entry.key.authority}" /><br/>
        </g:each>
    </div>
</g:if>
