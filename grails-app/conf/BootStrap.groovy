import com.fruiz.tankmon.*

class BootStrap {

    def init = { servletContext ->
        log.info('Inicializando TANKMON')
        log.debug('Validando ROLES')
        def rolAdmin = Rol.findByAuthority('ROLE_ADMIN')
        if (Rol.count() != 2) {
            if (!rolAdmin) {
                rolAdmin = new Rol(authority: 'ROLE_ADMIN').save()
            }
            def rolCliente = Rol.findByAuthority('ROLE_CLIENTE')
            if (!rolCliente) {
                rolCliente = new Rol(authority: 'ROLE_CLIENTE').save()
            }
        }

        log.debug('Validando CENTERON')
        def empresa = Empresa.findByNombre('SIN ASIGNAR')
        if (!empresa) {
            empresa = new Empresa(
                nombre: 'SIN ASIGNAR'
                , razonSocial: 'SIN ASIGNAR'
                , rfc: 'SINASIGNAR01'
            ).save()
        }

        log.debug('Validando USUARIOS')
        def admin = UsuarioRol.findByRol(rolAdmin)
        if (!admin) {
            empresa = Empresa.findByNombre('BASE')
            if (!empresa) {
                empresa = new Empresa(
                    nombre: 'BASE'
                    , razonSocial: 'BASE'
                    , rfc: 'BASE00000001'
                ).save()
            }
            admin = new Usuario(
                username:'admin'
                , password : 'admin'
                , nombre:'Admin' 
                , apellido:'User'
                , correo:'portal@um.edu.mx'
                , empresa: empresa
            ).save()

            UsuarioRol.create(admin, rolAdmin, true)

        }

        log.info('TANKMON Inicializado')
    }

    def destroy = {
    }
}
