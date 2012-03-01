package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

@Secured(['ROLE_CLIENTE'])
class PerfilController {

    def springSecurityService
    def index() {
        def empresas
        if (SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
            empresas = Empresa.list()
        } else {
            empresas = [springSecurityService.currentUser.empresa]
        }
        return [empresas: empresas, usuario: springSecurityService.currentUser]
    }

    def actualiza() {
        def usuario = Usuario.get(params.id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect uri: '/'
            return
        }

        usuario.password = params.password
        if (SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
            usuario.empresa = Empresa.get(params.empresa.id)
        }

        try {
            usuario.save(flush:true)
            session.empresa = usuario.empresa.nombre
        } catch(Exception e) {
            log.error("No se pudo actualizar el perfil del usuario $usuario",e)
            redirect uri: '/'
            return
        }

        flash.message = message(code: 'perfil.updated.message', args: [usuario.username])
        redirect uri: '/'
        
    }
}
