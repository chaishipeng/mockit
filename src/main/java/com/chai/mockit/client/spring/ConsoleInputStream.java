package com.chai.mockit.client.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ConsoleInputStream extends InputStream {

    List<InputStream> inputStreamList;

    public void addIs(InputStream is){
        inputStreamList.add(is);
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
