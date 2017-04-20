package com.chai.mockit.common.parsepack;

import java.io.*;

/**
 * Created by chaishipeng on 2017/4/20.
 */
public class PackParse {

    protected Object readPack(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream (is);
        Object obj = ois.readObject();
        return obj;
    }

    protected void writePack(OutputStream os, Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(obj);
        oos.flush();
    }

}
