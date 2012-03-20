<html>
<head>
	<meta name='layout' content='bootstrap'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div id='login'>
	<div class='inner'>
		<g:if test='${flash.message}'>
        <div class='alert <g:if test="${flash.messageStyle}">${flash.messageStyle}</g:if><g:else>alert-error</g:else>'>${flash.message}</div>
		</g:if>

        <fieldset>
            <form action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
                <div class="row-fluid">
                    <div class="control-group">
                        <label for='username'><g:message code="springSecurity.login.username.label"/></label>
                        <input type='text' class='span4' name='j_username' id='username' required='required'/>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="control-group">
                        <label for='password'><g:message code="springSecurity.login.password.label"/></label>
                        <input type='password' class='span4' name='j_password' id='password' required='required'/>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="control-group">
                        <g:message code="springSecurity.login.remember.me.label"/>&nbsp;<input type='checkbox' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
                    </div>
                </div>

                <div class="row-fluid" style='margin-top:10px;' >
                    <p>
                        <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}' class="btn btn-large btn-primary" style="width:150px;" />
                        <a href="${createLink(action:'olvido')}" class='btn btn-large' style="width:150px;">¿Lo olvidó?</a>
                    </p>
                </div>
            </form>
        </fieldset>
	</div>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
