package com.fruiz.tankmon

import org.springframework.dao.DataIntegrityViolationException

class EmpresaController {

    static allowedMethods = [crea: ['POST'], actualiza: ['POST'], elimina: 'POST']

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
        if (!empresaInstance.save(flush:true)) {
            render view: 'nueva', model: [empresa: empresa]
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.nombre])
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

    def create() {
		switch (request.method) {
		case 'GET':
        	[empresaInstance: new Empresa(params)]
			break
		case 'POST':
	        def empresaInstance = new Empresa(params)
	        if (!empresaInstance.save(flush: true)) {
	            render view: 'create', model: [empresaInstance: empresaInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
	        redirect action: 'show', id: empresaInstance.id
			break
		}
    }

    def show() {
        def empresaInstance = Empresa.get(params.id)
        if (!empresaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
            return
        }

        [empresaInstance: empresaInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def empresaInstance = Empresa.get(params.id)
	        if (!empresaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [empresaInstance: empresaInstance]
			break
		case 'POST':
	        def empresaInstance = Empresa.get(params.id)
	        if (!empresaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (empresaInstance.version > version) {
	                empresaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'empresa.label', default: 'Empresa')] as Object[],
	                          "Another user has updated this Empresa while you were editing")
	                render view: 'edit', model: [empresaInstance: empresaInstance]
	                return
	            }
	        }

	        empresaInstance.properties = params

	        if (!empresaInstance.save(flush: true)) {
	            render view: 'edit', model: [empresaInstance: empresaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
	        redirect action: 'show', id: empresaInstance.id
			break
		}
    }

    def delete() {
        def empresaInstance = Empresa.get(params.id)
        if (!empresaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
            return
        }

        try {
            empresaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
