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
            Tanque.withTransaction {
                tanque.empresa = springSecurityService.currentUser.empresa
                tanque.capacidad = new BigDecimal(params.capacidad)
                tanque.largo = new BigDecimal(params.largo)
                tanque.ancho = new BigDecimal(params.ancho)
                tanque.profundo = new BigDecimal(params.profundo)
                tanque.diametro = new BigDecimal(params.diametro)
                tanque.capacidadLleno = new BigDecimal(params.capacidadLleno)
                tanque.capacidadVacio = new BigDecimal(params.capacidadVacio)
                tanque.latitud = new BigDecimal(params.latitud)
                tanque.longitud = new BigDecimal(params.longitud)
                tanque.save(flush:true)

                def xtanque = new XTanque(tanque.properties)
                xtanque.capacidad = new BigDecimal(params.capacidad)
                xtanque.largo = new BigDecimal(params.largo)
                xtanque.ancho = new BigDecimal(params.ancho)
                xtanque.profundo = new BigDecimal(params.profundo)
                xtanque.diametro = new BigDecimal(params.diametro)
                xtanque.capacidadLleno = new BigDecimal(params.capacidadLleno)
                xtanque.capacidadVacio = new BigDecimal(params.capacidadVacio)
                xtanque.latitud = new BigDecimal(params.latitud)
                xtanque.longitud = new BigDecimal(params.longitud)
                xtanque.tanqueId = tanque.id
                xtanque.empresaId = tanque.empresa.id
                xtanque.save()
            }
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

        def historial = XTanque.findAllByTanqueId(tanque.id, [max:5,sort:'id',order:'desc'])
        log.debug("Historial : ${historial}")
        def puntos = []
        def data = [:]
        def labels = [:]
        def cont = 0
        for(xtanque in historial) {
            puntos << xtanque.capacidadLleno
            labels."${cont++}" = xtanque.dateCreated.toString()
        }
        data."${tanque.nombre}" = puntos

        log.debug "DATA: ${data}"
        log.debug "Labels: ${labels}"

        [tanque: tanque, data: data, labels: labels]
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
            Tanque.withTransaction {
                log.debug("Latitud : $params.latitud")
                log.debug("Longitud : $params.longitud")
                tanque.capacidad = new BigDecimal(params.capacidad)
                tanque.largo = new BigDecimal(params.largo)
                tanque.ancho = new BigDecimal(params.ancho)
                tanque.profundo = new BigDecimal(params.profundo)
                tanque.diametro = new BigDecimal(params.diametro)
                tanque.capacidadLleno = new BigDecimal(params.capacidadLleno)
                tanque.capacidadVacio = new BigDecimal(params.capacidadVacio)
                tanque.latitud = new BigDecimal(params.latitud)
                tanque.longitud = new BigDecimal(params.longitud)
                tanque.empresa = springSecurityService.currentUser.empresa
                tanque.save(flush:true)

                def xtanque = new XTanque(tanque.properties)
                xtanque.capacidad = new BigDecimal(params.capacidad)
                xtanque.largo = new BigDecimal(params.largo)
                xtanque.ancho = new BigDecimal(params.ancho)
                xtanque.profundo = new BigDecimal(params.profundo)
                xtanque.diametro = new BigDecimal(params.diametro)
                xtanque.capacidadLleno = new BigDecimal(params.capacidadLleno)
                xtanque.capacidadVacio = new BigDecimal(params.capacidadVacio)
                xtanque.latitud = new BigDecimal(params.latitud)
                xtanque.longitud = new BigDecimal(params.longitud)
                xtanque.empresaId = tanque.empresa.id
                xtanque.tanqueId = tanque.id
                xtanque.save()
            }
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

            def xtanque = new XTanque(tanque.properties)
            xtanque.tanqueId = tanque.id
            xtanque.empresaId = empresa.id
            xtanque.save()
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

            def xtanque = new XTanque(tanque.properties)
            xtanque.tanqueId = tanque.id
            xtanque.empresaId = empresa.id
            xtanque.save()

            flash.message = "Tanque ${tanque.nombre} ha sido desasignado"
        } else {
            flash.message = "Tanque ${params.id} no encontrado"
        }
        redirect action: 'lista'
    }
}
