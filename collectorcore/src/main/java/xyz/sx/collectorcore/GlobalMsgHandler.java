package xyz.sx.collectorcore;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Map;
import java.util.WeakHashMap;

public class GlobalMsgHandler {
    private static final Map<GlobalMsgListener, Integer> listeners = new WeakHashMap<>();

    private static final Handler msgHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            dispatch(msg);
        }
    };

    private static void dispatch(Message msg) {
        for (GlobalMsgListener li : listeners.keySet()) {
            if (li != null)
                li.OnReceivedMsg(msg);
        }
    }

    public interface GlobalMsgListener {
        void OnReceivedMsg(Message msg);
    }

    public static void sendMsg(Message msg) {
        msgHandler.sendMessage(msg);
    }

    public static void registerMsgListener(GlobalMsgListener listener) {
        if (listener != null) {
            synchronized (GlobalMsgHandler.class) {
                listeners.put(listener, 0);
            }
        }
    }

    public static void unregisterMsgListener(GlobalMsgListener listener) {
        if (listener != null && listeners.containsKey(listener)) {
            synchronized (GlobalMsgHandler.class) {
                listeners.remove(listener);
            }
        }
    }

    public static void sendErrorMsg(String s) {
        Log.d("ErrMsg", s);
        Message msg = new Message();
        msg.what = 0;
        msg.obj = s;
        sendMsg(msg);
    }
}
