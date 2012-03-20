package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.keygen.KeyGenerators
import uk.co.desirableobjects.sendgrid.*

@Secured(['ROLE_ADMIN'])
class UsuarioController {

    def springSecurityService
    def sendGridService

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
        def roles = obtieneRoles(null)
        [usuario: usuario, roles: roles]
    }

    def crea() {
        def usuario = new Usuario(params)
        try {
            Usuario.withTransaction {
                usuario.empresa = springSecurityService.currentUser.empresa
                String password = usuario.password
                if (!password) {
                    password = KeyGenerators.string().generateKey()
                    usuario.password = password
                }
                usuario.save(flush:true)
                def roles = asignaRoles(params)
                for(rol in roles) {
                    UsuarioRol.create(usuario, rol, false)
                }
                enviaCorreo(usuario, password)
            }
        } catch(ValidationException e) {
            def roles = obtineRoles(null)
            render view: 'nuevo', model: [usuario: usuario, roles: roles]
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

        def roles = obtieneRoles(usuario)
        [usuario: usuario, roles: roles]
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
                def roles = obtieneRoles(usuario)
                render view: 'edita', model: [usuario: usuario, roles: roles]
                return
            }
        }

        usuario.properties = params

        try {
            Usuario.withTransaction {
                usuario.empresa = springSecurityService.currentUser.empresa
                usuario.save(flush:true)
                UsuarioRol.removeAll(usuario)
                def roles = asignaRoles(params)
                for(rol in roles) {
                    UsuarioRol.create(usuario, rol, false)
                }
            }
        } catch(ValidationException e) {
            def roles = obtieneRoles(usuario)
            render view: 'edita', model: [usuario: usuario, roles: roles]
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
                Usuario.withTransaction {
                    UsuarioRol.removeAll(usuario)
                    usuario.delete(flush: true)
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
                    redirect action: 'lista'
                }
            } else {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
                redirect action: 'ver', id: params.id
            }
        } catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username])
            redirect action: 'ver', id: params.id
        }
    }

    def obtieneRoles(usuario) {
        log.debug "Obteniendo lista de roles"
        def roles = Rol.list()

        roles.sort { r1, r2 ->
            r1.authority <=> r2.authority
        }
        Set userRoleNames = []
        for (role in usuario?.authorities) {
            userRoleNames << role.authority
        }
        LinkedHashMap<Rol, Boolean> roleMap = [:]
        for (role in roles) {
            roleMap[(role)] = userRoleNames.contains(role.authority)
        }
        return roleMap
    }

    def asignaRoles(params) {
        def roles = [] as Set
        if (params.ROLE_ADMIN) {
            roles << Rol.findByAuthority('ROLE_ADMIN')
        } else {
            roles << Rol.findByAuthority('ROLE_CLIENTE')
        }
        return roles
    }

    def enviaCorreo(usuario, password) {
        def email = new SendGridEmailBuilder()
        email.from('David Mendoza', 'jdmendoza@um.edu.mx')
        email.to("${usuario.nombre} ${usuario.apellido}", "${usuario.correo}")
        email.replyTo('jdmendoza@um.edu.mx')
        email.subject("Se ha creado su cuenta")
        email.withText("Se ha creado su cuenta en http://tankmon.cloudfoundry.com.\n\nSu usuario es ${usuario.username}, su contraseña es ${password}.\n\nFRuiz e Hijos")
        email.withHtml("<html><head><title>Se ha creado su cuenta ${usuario.username}</title></head><body><p>Se ha creado su cuenta en <a href='http://tankmon.cloudfoundry.com'>TANKMON</a><br/>Usuario: ${usuario.username}<br/>Contraseña: ${password}<br/></p><br/><br/><h3>FRuiz e Hijos</h3></body></html>")
        sendGridService.send(email.build())
    }
}
