package com.fruiz.tankmon

import groovy.xml.dom.DOMCategory
import javax.xml.soap.*

class CenteronDaemonService  {

    def process() {
        log.debug("Ejecutando demonio de conexion a WebService de Centeron")
        try {
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();
            
            //Next, create the actual message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            
            //Create objects for the message parts            
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "http://webviewctl.centeron.net/GetTankAssets");
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();

            //Populate the body
            //Create the main element and namespace
            SOAPElement bodyElement =
                    body.addChildElement(envelope.createName("GetTankAssets",
                    "tns",
                    "http://webviewctl.centeron.net/"));
            //Add content
            bodyElement.addChildElement(envelope.createName("Username","tns","http://webviewctl.centeron.net/")).addTextNode("petroleum");
            bodyElement.addChildElement(envelope.createName("Password","tns","http://webviewctl.centeron.net/")).addTextNode("petroleum");

            //Save the message
            message.saveChanges();

            //Set the destination
            String destination =
                    "https://www.centeron.net/GetXMLData/webviewctl.asmx";
            //Send the message
            SOAPMessage reply = connection.call(message, destination);

            def doc = reply.getSOAPBody().extractContentAsDocument()
            def records = doc.documentElement

            def empresa = Empresa.findByNombre('SIN ASIGNAR')
            use(DOMCategory) {
                def tanques = doc.getElementsByTagName('Table');
                tanques.each { remoto ->
                    def tanque = Tanque.findByAsignacion(remoto.AssetUID.text())
                    if (!tanque) {
                        tanque = new Tanque(
                            asignacion: remoto.AssetUID.text()
                            , serie: remoto.AssetUID.text()
                            , nombre: remoto.AssetName.text()
                            , fechaFabricacion: new Date()
                            , tipo : remoto.Product.text()
                            , producto : remoto.Product.text()
                            , unidades : remoto.UnitDesc.text()
                            , capacidadLleno : new BigDecimal(remoto.PercentFull.text())
                            , capacidadVacio : new BigDecimal(remoto.PercentEmpty.text())
                            , capacidad : (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                            , empresa : empresa
                        ).save(flush:true)

                        def xtanque = new XTanque(tanque.properties)
                        xtanque.id = null
                        xtanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        xtanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        xtanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                        xtanque.tanqueId = tanque.id
                        xtanque.empresaId = empresa.id
                        xtanque.save()
                    } else {
                        tanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        tanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        tanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                        tanque.save(flush:true)

                        def xtanque = new XTanque(tanque.properties)
                        xtanque.id = null
                        xtanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        xtanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        xtanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal(tanque.capacidad)
                        xtanque.tanqueId = tanque.id
                        xtanque.empresaId = empresa.id
                        xtanque.save()
                    }
                }
            }

            connection.close();
        } catch(Exception e) {
            log.error("Error con el webservice", e)
        }

        log.debug('Termino sincronizacion')
    }
}
