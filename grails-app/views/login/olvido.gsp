<html>
<head>
	<meta name='layout' content='bootstrap'/>
	<title>Olvidó contraseña</title>
</head>

<body>
<div id='login'>
	<div class='inner'>
		<g:if test='${flash.message}'>
			<div class='alert alert-error'>${flash.message}</div>
		</g:if>

        <fieldset>
            <form action='${createLink(action:"restaurar")}' method='POST' id='loginForm' autocomplete='off'>
                <div class="row-fluid">
                    <div class="control-group">
                        <label for='username'><h2>Usuario o Correo Electrónico registrado</h2></label>
                        <input type='text' class='span4' name='username' id='username' required='required' value="${params.username}" />
                    </div>
                </div>

                <div class="row-fluid" style='margin-top:10px;' >
                    <p>
                        <input type='submit' id="submit" value='Enviar nueva contraseña por correo' class="btn btn-large btn-primary" />
                    </p>
                </div>
            </form>
        </fieldset>
	</div>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['username'].focus();
	})();
	// -->
</script>
</body>
</html>
