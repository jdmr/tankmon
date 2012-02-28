package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured

class SecureController {

    def springSecurityService

    @Secured(['ROLE_ADMIN'])
    def index() { 
        render "Hola ADMINISTRADOR: ${springSecurityService.currentUser.username}"
    }

    @Secured(['ROLE_ADMIN'])
    def clientes() {
        render "Hola CLIENTE: ${springSecurityService.currentUser.username}"
    }
}
