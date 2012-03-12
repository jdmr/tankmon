package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class CenteronController {
    
    def springSecurityService
    def centeronDaemonService

    def index() {
    }

    def actualiza() {
        try {
            centeronDaemonService.process()
        } catch(Exception e) {
            log.error("Error con el webservice", e)
        }

        flash.message = 'Termino de actualizar tanques'
        redirect action:'index'
    }
}
