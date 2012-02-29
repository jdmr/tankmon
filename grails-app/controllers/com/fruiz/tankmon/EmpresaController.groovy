package com.fruiz.tankmon

import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

class EmpresaController {

    static allowedMethods = [crea: ['POST'], actualiza: ['POST'], delete: 'POST']

    def index() {
        redirect action: 'lista', params: params
    }

    def lista() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [empresaInstanceList: Empresa.list(params), empresaInstanceTotal: Empresa.count()]
    }

    def nueva() {
        def empresa = new Empresa(params)
        [empresa: empresa]
    }

    def crea() {
        def empresa = new Empresa(params)
        try {
            empresa.save(flush:true)
        } catch(ValidationException e) {
            render view: 'nueva', model: [empresa: empresa]
            return
        }

        flash.message = message(code: 'default2.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
        redirect action: 'ver', id: empresa.id
    }

    def ver() {
        def empresa = Empresa.get(params.id)
        if (!empresa) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect: 'lista'
            return
        }

        [empresa: empresa]
    }

    def edita() {
        def empresa = Empresa.get(params.id)
        if (!empresa) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'lista'
            return
        }

        [empresa: empresa]
    }

    def actualiza() {
        def empresa = Empresa.get(params.id)
        if (!empresa) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'lista'
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (empresa.version > version) {
                empresa.errors.rejectValue('version', 'default.optimistic.locking.failure',
                          [message(code: 'empresa.label', default: 'Empresa')] as Object[],
                          "Another user has updated this Empresa while you were editing")
                render view: 'edita', model: [empresa: empresa]
                return
            }
        }

        empresa.properties = params

        try {
            empresa.save(flush:true)
        } catch(ValidationException e) {
            render view: 'nueva', model: [empresa: empresa]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
        redirect action: 'ver', id: empresa.id
    }

    def delete() {
        def empresa = Empresa.get(params.id)
        if (!empresa) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'lista'
            return
        }

        try {
            empresa.delete(flush: true)
			flash.message = message(code: 'default2.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
            redirect action: 'lista'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
            redirect action: 'ver', id: params.id
        }
    }
}
