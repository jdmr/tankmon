package com.fruiz.tankmon

import static org.junit.Assert.*
import org.junit.*

class UsuarioControllerTests extends BaseIntegrationTest {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraMostrarListaDeUsuarios() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        for (i in 1..20) {
            def usuario = new Usuario(
                username : "TEST--$i"
                , password : "TEST--$i"
                , nombre : "TEST--$i"
                , apellido : "TEST--$i"
                , puesto : "TEST--$i"
                , telefono : "TEST--00000$i"
                , correo : "test.x.$i@test.com"
                , empresa : empresa
            )
            usuario.save(flush:true)
        }

        def controller = new UsuarioController()
        controller.index()
        assertEquals '/usuario/lista', controller.response.redirectedUrl

        def model = controller.lista()
        assertEquals 10, model.usuarios.size()
        assert 20 <= model.totalDeUsuarios
    }

    @Test
    void debieraCrearUsuario() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def controller = new UsuarioController()
        def model = controller.nuevo()
        assert model.usuario

        controller.params.username = 'TEST--1'
        controller.params.password = 'TEST--1'
        controller.params.nombre = 'TEST--0000001'
        controller.params.apellido = 'TEST--0000001'
        controller.params.correo = 'test.x.01@test.com'
        controller.crea()
        assert controller.response.redirectedUrl.startsWith('/usuario/ver')
    }

    @Test
    void debieraActualizarUsuario() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def usuario = new Usuario(
            username : 'TEST--1'
            , password : 'TEST--1'
            , nombre : 'TEST--1'
            , apellido : 'TEST--1'
            , correo : 'test.x.01@test.com'
            , empresa : empresa
        ).save()

        def controller = new UsuarioController()
        controller.params.id = usuario.id
        def model = controller.ver()
        assert model
        assert model.usuario
        assertEquals 'TEST--1', model.usuario.nombre

        controller.params.id = usuario.id
        model = controller.edita()
        assert model
        assert model.usuario
        assertEquals 'TEST--1', model.usuario.nombre

        controller.params.id = usuario.id
        controller.params.version = usuario.version
        controller.params.nombre = 'TEST--2'
        controller.actualiza()
        assertEquals "/usuario/ver/${usuario.id}".toString(), controller.response.redirectedUrl

        usuario.refresh()
        assertEquals 'TEST--2', usuario.nombre
    }

    @Test
    void debieraEliminarUsuario() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def usuario = new Usuario(
            username : 'TEST--1'
            , password : 'TEST--1'
            , nombre : 'TEST--1'
            , apellido : 'TEST--1'
            , correo : 'test.x.01@test.com'
            , empresa : empresa
        ).save()

        def controller = new UsuarioController()
        controller.params.id = usuario.id
        controller.elimina()
        assertEquals '/usuario/lista', controller.response.redirectedUrl

        def prueba = Usuario.get(usuario.id)
        assert !prueba
    }
}
