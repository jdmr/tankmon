package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_CLIENTE'])
class TanqueController {

    def springSecurityService

    def index() {
        redirect action: 'lista', params: params
    }

    def lista() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def empresa = springSecurityService.currentUser.empresa

        def tanques
        def cantidad
        if (params.filtro) {
            def filtro = params.filtro
            tanques = Tanque.buscaPorEmpresa(empresa).buscaPorFiltro(filtro).list(params)
            cantidad = Tanque.buscaPorEmpresa(empresa).buscaPorFiltro(filtro).count()
        } else {
            tanques = Tanque.findAllByEmpresa(empresa, params)
            cantidad = Tanque.countByEmpresa(empresa)
        }

        [tanques: tanques, totalDeTanques: cantidad]
    }

    def nuevo() {
        def tanque = new Tanque(params)
        [tanque: tanque]
    }

    def crea() {
        def tanque = new Tanque(params)
        try {
            tanque.empresa = springSecurityService.currentUser.empresa
            tanque.save(flush:true)
        } catch(ValidationException e) {
            render view: 'nuevo', model: [tanque: tanque]
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tanque.label', default: 'Tanque'), tanque.asignacion])
        redirect action: 'ver', id: tanque.id
    }

    def ver() {
        def tanque = Tanque.get(params.id)
        if (!tanque) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tanque.label', default: 'Tanque'), params.id])
            redirect: 'lista'
            return
        }

        [tanque: tanque]
    }

    def edita() {
        log.debug("Editando tanque $params.id")
        def tanque = Tanque.get(params.id)
        if (!tanque) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tanque.label', default: 'Tanque'), params.id])
            redirect action: 'lista'
            return
        }

        [tanque: tanque]
    }

    def actualiza() {
        def tanque = Tanque.get(params.id)
        if (!tanque) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tanque.label', default: 'Tanque'), params.id])
            redirect action: 'lista'
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (tanque.version > version) {
                tanque.errors.rejectValue('version', 'default.optimistic.locking.failure',
                          [message(code: 'tanque.label', default: 'Tanque')] as Object[],
                          "Another user has updated this Tanque while you were editing")
                render view: 'edita', model: [tanque: tanque]
                return
            }
        }

        tanque.properties = params

        try {
            tanque.empresa = springSecurityService.currentUser.empresa
            tanque.save(flush:true)
        } catch(ValidationException e) {
            render view: 'edita', model: [tanque: tanque]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tanque.label', default: 'Tanque'), tanque.asignacion])
        redirect action: 'ver', id: tanque.id
    }

    def elimina() {
        def tanque = Tanque.get(params.id)
        if (!tanque) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'tanque.label', default: 'Tanque'), params.id])
            redirect action: 'lista'
            return
        }

        try {
            if (tanque != springSecurityService.currentUser) {
                tanque.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tanque.label', default: 'Tanque'), tanque.asignacion])
                redirect action: 'lista'
            } else {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tanque.label', default: 'Tanque'), tanque.asignacion])
                redirect action: 'ver', id: params.id
            }
        } catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tanque.label', default: 'Tanque'), tanque.asignacion])
            redirect action: 'ver', id: params.id
        }
    }

    @Secured(['ROLE_ADMIN'])
    def asignables() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def empresa = Empresa.findByNombre('CENTERON')

        for(tanqueId in params.asignaciones) {
            def tanque = Tanque.get(tanqueId)
            log.debug("Asignando tanque ${tanque.nombre}")
            tanque.empresa = springSecurityService.currentUser.empresa
            tanque.save(flush:true)
        }

        def tanques
        def cantidad
        if (params.filtro) {
            def filtro = params.filtro
            tanques = Tanque.buscaPorEmpresa(empresa).buscaPorFiltro(filtro).list(params)
            cantidad = Tanque.buscaPorEmpresa(empresa).buscaPorFiltro(filtro).count()
        } else {
            tanques = Tanque.findAllByEmpresa(empresa, params)
            cantidad = Tanque.countByEmpresa(empresa)
        }

        [tanques: tanques, totalDeTanques: cantidad]
    }

    @Secured(['ROLE_ADMIN'])
    def desasigna() {
        def tanque = Tanque.get(params.id)
        if (tanque) {
            def empresa = Empresa.findByNombre('CENTERON')
            tanque.empresa = empresa
            tanque.save(flush:true)

            flash.message = "Tanque ${tanque.nombre} ha sido desasignado"
        } else {
            flash.message = "Tanque ${params.id} no encontrado"
        }
        redirect action: 'lista'
    }
}
