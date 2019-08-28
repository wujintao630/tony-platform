package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.core.NotifyListener;
import com.tonytaotao.rpc.core.URL;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ZookeeperRegistry extends AbstractRegistry {


    private final ReentrantLock clientLock = new ReentrantLock();

    private final ReentrantLock serverLock = new ReentrantLock();

    private final ConcurrentHashMap<URL, ConcurrentHashMap<NotifyListener, IZkChildListener>> serviceListenerMap = new ConcurrentHashMap<>();

    private ZkClient zkClient;

    public ZookeeperRegistry(URL url, ZkClient zkClient) {
        super(url);
        this.zkClient = zkClient;

        IZkStateListener zkStateListener = new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {

            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("zk get new session nofity");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

            }
        };

        this.zkClient.subscribeStateChanges(zkStateListener);

    }

    @Override
    protected void doRegister(URL url) {

    }

    @Override
    protected void doUnregister(URL url) {

    }

    @Override
    protected void doSubscribe(URL url, NotifyListener listener) {

    }

    @Override
    protected void doUnsubscribe(URL url, NotifyListener listener) {

    }

    @Override
    protected List<URL> doDiscover(URL url) {
        return null;
    }

    @Override
    public void close() throws Exception {
        this.zkClient.close();
    }
}
