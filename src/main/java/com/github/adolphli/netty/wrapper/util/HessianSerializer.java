package com.github.adolphli.netty.wrapper.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer {

    private static final SerializerFactory serializerFactory = new SerializerFactory();

    /**
     * 对Object进行序列化
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.setSerializerFactory(serializerFactory);
        output.writeObject(obj);
        output.close();

        byte[] bytes = byteArray.toByteArray();
        return bytes;
    }

    /**
     * 将byte数组反序列化成Object
     */
    public static <T> T deserialize(byte[] data) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        input.setSerializerFactory(serializerFactory);
        Object resultObject;
        resultObject = input.readObject();
        input.close();
        return (T) resultObject;
    }
}