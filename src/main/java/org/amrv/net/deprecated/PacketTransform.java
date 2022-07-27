package org.amrv.net.deprecated;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface PacketTransform<I, O> {

    byte[] disassemble(I data);

    O assemble(byte[] bytes);

}
