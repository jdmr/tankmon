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

        log.debug('Validando USUARIOS')
        def admin = UsuarioRol.findByRol(rolAdmin)
        if (!admin) {
            def empresa = Empresa.findByNombre('BASE')
            if (!empresa) {
                empresa = new Empresa(
                    nombre: 'BASE'
                    , razonSocial: 'BASE'
                    , rfc: 'BASE00000001'
                ).save()
            }
            admin = new Usuario(
                username:'portal@um.edu.mx'
                , password : 'admin'
                , nombre:'Admin' 
                , apellido:'User'
                , empresa: empresa
            ).save()

            UsuarioRol.create(admin, rolAdmin, true)
        }

        log.info('TANKMON Inicializado')
    }

    def destroy = {
    }
}
