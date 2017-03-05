package com.github.adolphli.netty.wrapper.protocol;

public class Message {

    /**
     * 消息id
     */
    private int id;

    /**
     * 传输对象头部，用于扩展， 当前版本总为null
     */
    private byte[] header;

    /**
     * 传输对象主体
     */
    private byte[] body;

    public Message(int id, byte[] header, byte[] body) {
        this.id = id;
        this.header = header;
        this.body = body;
    }

    /**
     * 返回头部长度
     */
    public int getHeaderLength() {

        if (header == null) {
            return 0;
        }
        return header.length;
    }

    /**
     * 返回主体部分长度
     */
    public int getBodyLength() {

        if (body == null) {
            return 0;
        }

        return body.length;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
