package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.core.NotifyListener;
import com.tonytaotao.rpc.core.URL;
import com.tonytaotao.rpc.utils.ConcurrentHashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractRegistry implements Registry{

    protected Set<URL> registeredServiceUrlSet = new ConcurrentHashSet<>();

    private URL registerUrl;

    public AbstractRegistry(URL url) {
        this.registerUrl = url.clone0();
    }

    @Override
    public void subscribe(URL url, NotifyListener notifyListener) throws Exception {
        doSubscribe(url.clone0(), notifyListener);
    }

    @Override
    public void unsubscribe(URL url, NotifyListener notifyListener) throws Exception {
        doUnsubscribe(url.clone0(), notifyListener);
    }

    @Override
    public void register(URL url) throws Exception {
        doRegister(url.clone0());
        registeredServiceUrlSet.add(url);
    }

    @Override
    public void unregister(URL url) throws Exception {
        doUnregister(url.clone0());
        registeredServiceUrlSet.remove(url);

    }

    @Override
    public URL getUrl() throws Exception {
        return registerUrl;
    }

    @Override
    public List<URL> discover(URL url) throws Exception {
        List<URL> results = new ArrayList<>();
        List<URL> urlDiscoveredList = doDiscover(url);
        if (urlDiscoveredList != null) {
            urlDiscoveredList.forEach(u -> results.add(u.clone0()));
        }
        return results;
    }

    protected abstract void doRegister(URL url);

    protected abstract void doUnregister(URL url);

    protected abstract void doSubscribe(URL url, NotifyListener listener);

    protected abstract void doUnsubscribe(URL url, NotifyListener listener);

    protected abstract List<URL> doDiscover(URL url);
}
