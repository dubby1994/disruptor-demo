package cn.dubby.disruptor.basic;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by teeyoung on 17/10/27.
 */
public class MultiProductorLongEventMain {

    private static final Translator TRANSLATOR = new Translator();

    public static void main(String[] args) throws Exception {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, executor, ProducerType.MULTI, new BlockingWaitStrategy());

        // Connect the handler
        disruptor.handleEventsWith(new LongEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        new Thread(){
            @Override
            public void run() {
                RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

                ByteBuffer bb = ByteBuffer.allocate(8);
                for (long l = 0; l < 10; l++) {
                    bb.putLong(0, l);
                    ringBuffer.publishEvent(TRANSLATOR, bb);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

                ByteBuffer bb = ByteBuffer.allocate(8);
                for (long l = 10; l < 20; l++) {
                    bb.putLong(0, l);
                    ringBuffer.publishEvent(TRANSLATOR, bb);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

                ByteBuffer bb = ByteBuffer.allocate(8);
                for (long l = 20; l < 30; l++) {
                    bb.putLong(0, l);
                    ringBuffer.publishEvent(TRANSLATOR, bb);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

}
