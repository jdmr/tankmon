package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_CLIENTE'])
class InicioController {

    def index() { 
        log.debug("Cargando pagina de inicio")
    }
}
