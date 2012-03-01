package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class UsuarioController {

    def springSecurityService

    def index() {
        redirect action: 'lista', params: params
    }

    def lista() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def empresa = springSecurityService.currentUser.empresa
        [usuarios: Usuario.findAllByEmpresa(empresa, params), totalDeUsuarios: Usuario.countByEmpresa(empresa)]
    }

    def nuevo() {
        def usuario = new Usuario(params)
        [usuario: usuario]
    }

    def crea() {
        def usuario = new Usuario(params)
        try {
            usuario.empresa = springSecurityService.currentUser.empresa
            usuario.save(flush:true)
        } catch(ValidationException e) {
            render view: 'nuevo', model: [usuario: usuario]
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
        redirect action: 'ver', id: usuario.id
    }

    def ver() {
        def usuario = Usuario.get(params.id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect: 'lista'
            return
        }

        [usuario: usuario]
    }

    def edita() {
        log.debug("Editando usuario $params.id")
        def usuario = Usuario.get(params.id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect action: 'lista'
            return
        }

        [usuario: usuario]
    }

    def actualiza() {
        def usuario = Usuario.get(params.id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect action: 'lista'
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (usuario.version > version) {
                usuario.errors.rejectValue('version', 'default.optimistic.locking.failure',
                          [message(code: 'usuario.label', default: 'Usuario')] as Object[],
                          "Another user has updated this Usuario while you were editing")
                render view: 'edita', model: [usuario: usuario]
                return
            }
        }

        usuario.properties = params

        try {
            usuario.empresa = springSecurityService.currentUser.empresa
            usuario.save(flush:true)
        } catch(ValidationException e) {
            render view: 'edita', model: [usuario: usuario]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
        redirect action: 'ver', id: usuario.id
    }

    def elimina() {
        def usuario = Usuario.get(params.id)
        if (!usuario) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect action: 'lista'
            return
        }

        try {
            if (usuario != springSecurityService.currentUser) {
                usuario.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
                redirect action: 'lista'
            } else {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
                redirect action: 'ver', id: params.id
            }
        } catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
            redirect action: 'ver', id: params.id
        }
    }
}
