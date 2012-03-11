package com.fruiz.tankmon

import static org.junit.Assert.*
import org.junit.*

class TanqueControllerTests extends BaseIntegrationTest {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraMostrarListaDeTanques() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        for (i in 1..20) {
            def tanque = new Tanque(
                asignacion : "TEST--$i"
                , nombre : "TEST--$i"
                , serie : "TEST--$i"
                , fechaFabricacion : new Date()
                , producto : "TEST--$i"
                , unidades : "Lts"
                , tipo : "TEST--$i"
                , empresa : empresa
            )
            tanque.save(flush:true)
        }

        def controller = new TanqueController()
        controller.index()
        assertEquals '/tanque/lista', controller.response.redirectedUrl

        def model = controller.lista()
        assertEquals 10, model.tanques.size()
        assert 20 <= model.totalDeTanques
    }

    @Test
    void debieraCrearTanque() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def controller = new TanqueController()
        def model = controller.nuevo()
        assert model.tanque

        controller.params.nombre = 'TEST--1'
        controller.params.asignacion = 'TEST--1'
        controller.params.serie = 'TEST--1'
        controller.params.fechaFabricacion = new Date()
        controller.params.producto = 'TEST--0000001'
        controller.params.unidades = 'Lts'
        controller.params.tipo = 'TEST--1'
        controller.crea()
        assert controller.response.redirectedUrl.startsWith('/tanque/ver')
    }

    @Test
    void debieraActualizarTanque() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def tanque = new Tanque(
            asignacion : 'TEST--1'
            , nombre : 'TEST--1'
            , serie : 'TEST--1'
            , fechaFabricacion : new Date()
            , producto : 'TEST--1'
            , unidades : 'Lts'
            , tipo : 'TEST--1'
            , empresa : empresa
        ).save()

        def controller = new TanqueController()
        controller.params.id = tanque.id
        def model = controller.ver()
        assert model
        assert model.tanque
        assertEquals 'TEST--1', model.tanque.producto

        controller.params.id = tanque.id
        model = controller.edita()
        assert model
        assert model.tanque
        assertEquals 'TEST--1', model.tanque.producto

        controller.params.id = tanque.id
        controller.params.version = tanque.version
        controller.params.producto = 'TEST--2'
        controller.actualiza()
        assertEquals "/tanque/ver/${tanque.id}".toString(), controller.response.redirectedUrl

        tanque.refresh()
        assertEquals 'TEST--2', tanque.producto
    }

    @Test
    void debieraEliminarTanque() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        authenticateAdmin(empresa)

        def tanque = new Tanque(
            asignacion : 'TEST--1'
            , nombre : 'TEST--1'
            , serie : 'TEST--1'
            , fechaFabricacion : new Date()
            , producto : 'TEST--1'
            , unidades : 'Lts'
            , tipo : 'TEST--1'
            , empresa : empresa
        ).save()

        def controller = new TanqueController()
        controller.params.id = tanque.id
        controller.elimina()
        assertEquals '/tanque/lista', controller.response.redirectedUrl

        def prueba = Tanque.get(tanque.id)
        assert !prueba
    }
}
