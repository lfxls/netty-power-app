/**
 * HexingWsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package tangdi.ws.service;

public interface HexingWsService extends javax.xml.rpc.Service {
    public java.lang.String getHexingWsPortAddress();

    public tangdi.ws.service.HexingWs getHexingWsPort() throws javax.xml.rpc.ServiceException;

    public tangdi.ws.service.HexingWs getHexingWsPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
