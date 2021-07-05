package com.chipmong.dms.socket

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import com.chipmong.dms.constants.Constants
import com.chipmong.dms.utils.DmsSharePreference
import com.chipmong.dms.utils.Logger
import com.chipmong.dms.utils.ServerConfig
import com.github.nkzawa.socketio.client.Ack
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 05-Feb-20
 */
class SocketClient private constructor(context: Activity, token: String) {
    private val TAG = SocketClient::class.java.simpleName

    private var mSocket: Socket? = null
    private val mActivity: Activity? = context
    private var mOnSocketClientCallBack: OnSocketClientCallBack? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var socketInstance: SocketClient? = null

        @Synchronized
        fun getInstance(context: Activity): SocketClient? {
            if (socketInstance == null) {
//                var token = DmsSharePreference.getInstance(context).getAccessToken()
//                val name = DmsSharePreference.getInstance(context).getUserModel()?.fullName
//                token = "token=$token&platform=Android&name=$name"
//                socketInstance = SocketClient(context, token)
            }
            return socketInstance
        }
    }

    init {
        onInitSocket(token)
        onConnecting()
    }


    private fun onInitSocket(token: String) {
        try {
//            Logger.log(TAG, "SOCKET TOKEN : $token")
            val options = IO.Options()
            options.forceNew = true
            options.query = token
            //            options.query = "token=" + token +"&platform=Android";
            //            options.transports =new String[]{WebSocket.NAME, Polling.NAME};
            //            options.transports = new String[]{/*WebSocket.NAME, "flashsocket", "htmlfile", "xhr-polling", "jsonp-polling"*/};
            //HttpsTrustManager.allowAllSSL();
            mSocket = IO.socket("", options)

            mSocket?.let { _socket ->
                _socket.on(Socket.EVENT_CONNECT) { args ->
                    mOnSocketClientCallBack?.let {
                        mOnSocketClientCallBack?.onSocketNotificationListener(Socket.EVENT_CONNECT, args)
                        it.onSocketConnect(args)
                    }
                }?.on(Socket.EVENT_DISCONNECT) { args ->
                    mOnSocketClientCallBack?.let {
                        it.onSocketNotificationListener(Socket.EVENT_DISCONNECT, args)
                        it.onSocketDisconnected(args)
                    }
                }?.on(Socket.EVENT_MESSAGE) { args ->
                    mOnSocketClientCallBack?.let {
                        if (!args.isNullOrEmpty()) {
                            mOnSocketClientCallBack?.onSocketNotificationListener(Socket.EVENT_MESSAGE, args[0])
                            it.onSocketReceiveMessage(args[0])
                        }
                    }
                }
            }
        } catch (e: URISyntaxException) {
            Log.d("Error_IO", e.message.toString())
        }

    }

    fun offAllEvent() {
        mSocket?.let {
            mSocket?.off(Socket.EVENT_CONNECT)?.off("bg_event")?.off(Socket.EVENT_DISCONNECT)?.off(Socket.EVENT_MESSAGE)
        }
    }

    /**
     * This function for connecting to socket server
     */
    private fun onConnecting() {
        mActivity?.runOnUiThread {
            mSocket?.connect()
        }
    }

    /***
     * This function for disconnecting from socket server.
     */
    fun onDisConnecting() {
        mActivity?.runOnUiThread {
            mSocket?.disconnect()
            socketInstance = null
        }
    }

    /**
     * This function for emit data to socket server.
     *
     * @param message
     */
    fun onEmit(message: JSONObject) {
        mActivity?.let {
            if (null == mSocket) {
//                Logger.log(TAG, "Socket Object is null")
                return
            }
            mSocket?.emit(Constants.SocketEvent.MESSAGE, message, Ack { args ->
                mOnSocketClientCallBack?.onAckCallBack(*args)
            })
//            Logger.log(
//                TAG,
//                "Socket Event socket has been emit: $message"
//            )
        }
    }

    /**
     * This function for emit data to socket server.
     *
     * @param message
     */
    fun onEmit(message: JSONObject, ack: Ack) {
        mActivity?.let {
            if (null != mSocket) {
                mSocket?.emit(Socket.EVENT_MESSAGE, message, ack)
//                Logger.log(
//                    TAG,
//                    "Socket has been emit: $message"
//                )
            } else {
//                Logger.log(TAG, "Socket Object is null")
            }
        }
    }

    /**
     * OnSocket Client Interface
     *
     * @param onSocketClientCallBack
     */
    fun setOnSocketClientCallBackListener(onSocketClientCallBack: OnSocketClientCallBack) {
        mOnSocketClientCallBack = onSocketClientCallBack
    }

    /**
     * This function user set callback listener to SocketClient Class.
     */
    interface OnSocketClientCallBack {
        fun onSocketNotificationListener(event_type: Any, data: Any) {}

        fun onSocketConnect(data: Any)
        fun onSocketDisconnected(data: Any)
        fun onSocketReceiveMessage(data: Any)

        fun onAckCallBack(vararg args: Any)
    }

    /**
     * How to request socket.
     * JSONObject message = new JSONObject();
     * JSONObject meta_data = new JSONObject();
     * try {
     * meta_data.put("id",3);
     * meta_data.put("message","Join Wedding");
     * meta_data.put("message_type","comment_reply");
     * message.put("category","news");
     * message.put("parent_id",1);
     * message.put("data",meta_data);
     * mSocketClient.onEmit(message);
     * } catch (JSONException e) {
     * e.printStackTrace();
     * }
     * String user_1 = "282bfb31680e11e58313d52f90b385e9";
     * mSocketClient = new SocketClient(user_1,this);
     * mSocketClient.setOnSocketClientCallBackListener(new SocketClient.OnSocketClientCallBack() {
     *
     * @Override public void onCallBack(Object event_type, Object data) {
     * Logger.startLog(this.getClass().getSimpleName(),"SOCKET: "+event_type.toString()+" - " + data.toString());
     * }
     * });
     */

    fun clear() {
        socketInstance = null
    }

    fun isSocketConnected(): Boolean {
        return mSocket != null && mSocket!!.connected()
    }
}