package com.lineate.async;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.handler.codec.http.QueryStringDecoder;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/*
 Выполнить задание 4 используя Netty.
 */

public class Task5HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final String CONTENT =
            "<html>" +
                    "<body>" +
                    "<h1>Answer = %s!</h1>" +
                    "</body>" +
                    "</html>";

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        String json = queryStringDecoder.parameters().get("json").get(0);

        java.util.regex.Pattern px = java.util.regex.Pattern.compile("^\\{x:(\\d+),.+\\}$");
        java.util.regex.Matcher mx = px.matcher(json);
        java.util.regex.Pattern py = java.util.regex.Pattern.compile("^\\{.+y:(\\d+)\\}$");
        java.util.regex.Matcher my = py.matcher(json);

        String valueX = mx.matches() ? mx.group(1) : null;
        System.out.println("value X: " + valueX);
        String valueY = my.matches() ? my.group(1) : null;
        System.out.println("value Y: " + valueY);

        System.out.println(json);
        String content = String.format(CONTENT, Integer.parseInt(valueX) + Integer.parseInt(valueY));

        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                OK,
                Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, "close");

        ctx.write(response);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
