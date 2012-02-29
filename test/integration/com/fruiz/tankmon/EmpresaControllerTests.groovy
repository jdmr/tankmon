package com.fruiz.tankmon

import static org.junit.Assert.*
import org.junit.*

class EmpresaControllerTests extends BaseIntegrationTest {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraMostrarListaDeEmpresas() {
        for (i in 1..20) {
            def empresa = new Empresa(
                nombre : "TEST--$i"
                , razonSocial : "TEST--$i"
                , rfc : "TEST--00000$i"
            )
            empresa.save()
        }

        def controller = new EmpresaController()
        controller.index()
        assertEquals '/empresa/lista', controller.response.redirectedUrl

        def model = controller.lista()
        assertEquals 10, model.empresas.size()
        assert 20 <= model.totalDeEmpresas
    }

    @Test
    void debieraCrearEmpresa() {
        def controller = new EmpresaController()
        def model = controller.nueva()
        assert model.empresa

        controller.params.nombre = 'TEST--1'
        controller.params.razonSocial = 'TEST--1'
        controller.params.rfc = 'TEST--0000001'
        controller.crea()
        assert controller.response.redirectedUrl.startsWith('/empresa/ver')
    }

    @Test
    void debieraActualizarEmpresa() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        def controller = new EmpresaController()
        controller.params.id = empresa.id
        def model = controller.ver()
        assert model
        assert model.empresa
        assertEquals 'TEST--1', model.empresa.nombre

        controller.params.id = empresa.id
        model = controller.edita()
        assert model
        assert model.empresa
        assertEquals 'TEST--1', model.empresa.nombre

        controller.params.id = empresa.id
        controller.params.version = empresa.version
        controller.params.nombre = 'TEST--2'
        controller.actualiza()
        assertEquals "/empresa/ver/${empresa.id}".toString(), controller.response.redirectedUrl

        empresa.refresh()
        assertEquals 'TEST--2', empresa.nombre
    }

    @Test
    void debieraEliminarEmpresa() {
        def empresa = new Empresa(
            nombre : 'TEST--1'
            , razonSocial : 'TEST--1'
            , rfc : 'TEST--0000001'
        ).save()

        def controller = new EmpresaController()
        controller.params.id = empresa.id
        controller.elimina()
        assertEquals '/empresa/lista', controller.response.redirectedUrl

        def prueba = Empresa.get(empresa.id)
        assert !prueba
    }
}
