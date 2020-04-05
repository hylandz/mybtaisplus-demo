package com.xlx.mpd.system.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * 毫秒转时间
 *
 * @author xielx at 2020/4/5 14:35
 */
public class Date2LongSerialize extends JsonSerializer<Long> {
    @SneakyThrows
    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(DateTimeUtil.secondsToDateTime(aLong));
    }
}
