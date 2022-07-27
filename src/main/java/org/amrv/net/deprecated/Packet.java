package org.amrv.net.deprecated;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface Packet<T> extends PacketTransform<T, T> {

    @Override
    byte[] disassemble(T data);

    @Override
    T assemble(byte[] bytes);

}
