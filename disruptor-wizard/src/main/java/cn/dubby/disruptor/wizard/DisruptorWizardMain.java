package cn.dubby.disruptor.wizard;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by teeyoung on 17/10/28.
 */
public class DisruptorWizardMain {

    private static final Translator TRANSLATOR = new Translator();

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        int bufferSize = 1024;

        EventHandler handler1 = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("handler1 : " + event.getValue());
            }
        };
        EventHandler handler2 = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("handler2 : " + event.getValue());
            }
        };

        EventHandler handler3 = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("handler3 : " + event.getValue() + " arrived. Handler1 and handler2 should have completed. Completed.\n");
            }
        };

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, executor);

        disruptor.handleEventsWith(handler1, handler2).then(handler3);

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(TRANSLATOR, bb);
            Thread.sleep(1000);
        }
    }


}
