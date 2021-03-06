package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class EmpresaController {

    def springSecurityService

    def index() {
        redirect action: 'lista', params: params
    }

    def lista() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def sinAsignar = Empresa.findByNombre('SIN ASIGNAR')
        def empresas = Empresa.list(params)
        empresas.remove(sinAsignar)
        [empresas: empresas, totalDeEmpresas: (Empresa.count() - 1)]
    }

    def nueva() {
        def empresa = new Empresa(params)
        [empresa: empresa]
    }

    def crea() {
        def empresa = new Empresa(params)
        try {
            Empresa.withTransaction {
                empresa.save(flush:true)
                def usuario = springSecurityService.currentUser
                usuario.empresa = empresa
                usuario.save(flush:true)
                session.empresa = empresa.nombre

                def archivo = request.getFile('imagen')
                if (!archivo.empty) {
                    byte[] f = archivo.bytes
                    def imagen = new Imagen (
                        nombre : archivo.originalFilename
                        , tipoContenido : archivo.contentType
                        , tamano : archivo.size
                        , archivo : f
                    )
                    if (empresa.imagenes) {
                        empresa.imagenes?.clear()
                    } else {
                        empresa.imagenes = []
                    }
                    empresa.imagenes << imagen
                    empresa.save()
                }
            }
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
        log.debug("Editando empresa $params.id")
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

        if (empresa.nombre != 'SIN ASIGNAR') {
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
                Empresa.withTransaction {
                    def archivo = request.getFile('imagen')
                    if (!archivo.empty) {
                        byte[] f = archivo.bytes
                        def imagen = new Imagen(
                            nombre : archivo.originalFilename
                            , tipoContenido : archivo.contentType
                            , tamano : archivo.size
                            , archivo : f
                        )
                        if (empresa.imagenes) {
                            empresa.imagenes?.clear()
                        } else {
                            empresa.imagenes = []
                        }
                        empresa.imagenes << imagen
                    }

                    empresa.save(flush:true)
                    def usuario = springSecurityService.currentUser
                    usuario.empresa = empresa
                    usuario.save(flush:true)
                    session.empresa = empresa.nombre
                }
            } catch(ValidationException e) {
                render view: 'edita', model: [empresa: empresa]
                return
            }

            flash.message = message(code: 'default2.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
            redirect action: 'ver', id: empresa.id
        } else {
            flash.message = 'No se puede modificar la empresa SIN ASIGNAR'
            redirect action: 'ver', id: empresa.id
        }
    }

    def elimina() {
        def empresa = Empresa.get(params.id)
        if (!empresa) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'lista'
            return
        }

        if (empresa.nombre != 'SIN ASIGNAR') {

            try {
                if (Empresa.count() > 1) {
                    Empresa.withTransaction {
                        def otraEmpresa = Empresa.findByIdNot(empresa.id)
                        def rolAdmin = Rol.findByAuthority('ROLE_ADMIN')
                        def usuarios = empresa.usuarios
                        def usuariosParaEliminar = []
                        for(usuario in usuarios) {
                            for(rol in usuario.authorities) {
                                if (rol == rolAdmin) {
                                    usuario.empresa = otraEmpresa
                                    usuario.save(flush:true)
                                } else {
                                    usuariosParaEliminar << usuario
                                }
                            }
                        }
                        empresa.usuarios?.clear()
                        for(usuario in usuariosParaEliminar) {
                            usuarios.remove(usuario)
                            UsuarioRol.removeAll(usuario)
                            usuario.delete(flush: true)
                        }
                        empresa.delete(flush: true)
                        flash.message = message(code: 'default2.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
                        redirect action: 'lista'
                    }
                } else {
                    flash.message = message(code: 'ultima.empresa.no.borrada.message', args: [empresa.nombre])
                    redirect action: 'lista'
                }
            } catch (DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
                redirect action: 'ver', id: params.id
            }

        } else {
            flash.message = 'No se puede eliminar la empresa SIN ASIGNAR'
            redirect action: 'ver', id: params.id
        }
    }

    def imagen() {
        try {
            def empresa = Empresa.get(params.id)
            def foto
            for(x in empresa?.imagenes) {
                foto = x
                break;
            }
            if (!foto) {
                def directorio = servletContext.getRealPath("/images")
                def file = new File("${directorio}/sin-foto.jpg")
                foto = new Imagen(
                    nombre : 'sin-foto.jpg'
                    , tipoContenido : 'image/jpeg'
                    , tamano : file.size()
                    , archivo : file.getBytes()
                )
            }
            response.contentType = foto.tipoContenido
            response.contentLength = foto.tamano
            response.outputStream << foto.archivo
        } catch(Exception e) {
            log.error("No se pudo obtener la imagen", e)
        }
    }
}
