package com.github.adolphli.netty.wrapper;

import com.github.adolphli.netty.wrapper.rpc.HandlerContext;
import com.github.adolphli.netty.wrapper.rpc.RequestProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentTest {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final String PREFIX = "Hello, ";

    @Test
    public void test() throws Exception {
        startServer();
        doTest();
    }

    private void doTest() throws Exception {
        final Client client = new DefaultCliet("127.0.0.1", 6666);

        int times = 500;
        final CountDownLatch taskDone = new CountDownLatch(times);
        final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
        for (int i = 0; i < times; i++) {
            final int index = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        People people = new People(Integer.toString(index), index);
                        People result = (People) client.invokeSync(people, 2000);
                        assertEquals(result.getAge(), index * 2);
                        assertEquals(result.getName(), PREFIX + Integer.toString(index));
                    } catch (Throwable e) {
                        e.printStackTrace();
                        exceptions.add(e);
                    } finally {
                        taskDone.countDown();
                    }
                }
            });
        }

        taskDone.await(60, TimeUnit.SECONDS);
        assertTrue(taskDone.getCount() == 0);
        assertEquals(0, exceptions.size());
    }

    private void startServer() throws Exception {
        Server server = new DefaultServer(6666);
        server.registerRequestProcessor(new RequestProcessor<People>() {
            public void handleRequest(HandlerContext handlerContext, People request) {
                People people = new People();
                people.setAge(request.getAge() * 2);
                people.setName(PREFIX + request.getName());
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                    handlerContext.sendResponse(people);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Executor getExecutor() {
                return executorService;
            }

            public String interest() {
                return People.class.getName();
            }
        });
        server.start();
    }
}
