package com.fruiz.tankmon

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.crypto.keygen.KeyGenerators
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import uk.co.desirableobjects.sendgrid.*

class LoginController {

    def sendGridService

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: 'auth', params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		String view = 'auth'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: view, model: [postUrl: postUrl,
		                           rememberMeParameter: config.rememberMe.parameter]
	}

	/**
	 * The redirect action for Ajax requests.
	 */
	def authAjax = {
		response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
		response.sendError HttpServletResponse.SC_UNAUTHORIZED
	}

	/**
	 * Show denied page.
	 */
	def denied = {
		if (springSecurityService.isLoggedIn() &&
				authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: 'full', params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		def config = SpringSecurityUtils.securityConfig
		render view: 'auth', params: params,
			model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'auth', params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess = {
		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied = {
		render([error: 'access denied'] as JSON)
	}

    def olvido() {
        log.debug("Olvido su contrasena")
    }

    def restaurar() {
        def usuario = Usuario.findByUsernameIlikeOrCorreoIlike(params.username,params.username)
        if (!usuario) {
            log.debug("No encontre una cuenta con ese usuario o correo")
            flash.message = "No encontre una cuenta con ese usuario o correo"
            redirect action:'olvido', params: params
        }
        Usuario.withTransaction {
            String password = KeyGenerators.string().generateKey()
            usuario.password = password
            usuario.save(flush:true)
            enviaCorreo(usuario, password)
            flash.message = "Se ha enviado un correo a ${usuario.correo} con su nueva contraseña"
            flash.messageStyle = 'alert-success'
            redirect action:'auth'
        }
    }

    def enviaCorreo(usuario, password) {
        def email = new SendGridEmailBuilder()
        email.from('David Mendoza', 'jdmendoza@um.edu.mx')
        email.to("${usuario.nombre} ${usuario.apellido}", "${usuario.correo}")
        email.replyTo('jdmendoza@um.edu.mx')
        email.subject("Se ha renovado su contraseña")
        email.withText("Se ha renovado su contraseña en http://tankmon.cloudfoundry.com.\n\nSu usuario es ${usuario.username}, su contraseña es ${password}.\n\nFRuiz e Hijos")
        email.withHtml("<html><head><title>Se ha renovado la contraseña de ${usuario.username}</title></head><body><p>Se ha renovado la contraseña de su usuario en <a href='http://tankmon.cloudfoundry.com'>TANKMON</a><br/>Usuario: ${usuario.username}<br/>Contraseña: ${password}<br/></p><br/><br/><h3>FRuiz e Hijos</h3></body></html>")
        sendGridService.send(email.build())
    }
}
