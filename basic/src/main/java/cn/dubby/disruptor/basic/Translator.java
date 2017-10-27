package cn.dubby.disruptor.basic;

import com.lmax.disruptor.EventTranslatorOneArg;

import java.nio.ByteBuffer;

/**
 * Created by teeyoung on 17/10/27.
 */
class Translator implements EventTranslatorOneArg<LongEvent, ByteBuffer> {
    @Override
    public void translateTo(LongEvent event, long sequence, ByteBuffer data) {
        event.set(data.getLong(0));
    }
}
