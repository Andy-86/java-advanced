package gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;

import java.nio.charset.StandardCharsets;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private String message;
    private FullHttpResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            message = buf.toString(StandardCharsets.UTF_8);
            buf.release();
            System.out.println(message);
        }
    }

    public String getMessage() {
        return message;
    }

    public FullHttpResponse getResponse() {
        return response;
    }
}
