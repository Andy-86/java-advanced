package gateway.outbound.netty4;

import gateway.filter.HttpHeaderFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.apache.http.HttpHeaders.CONNECTION;

public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    private final String host;
    private final int port;

    public NettyHttpClientOutboundHandler(String backendUrl) {
        String url = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        this.host = url.substring(url.indexOf(":") + 3, url.lastIndexOf(":"));
        this.port = Integer.parseInt(url.substring(url.lastIndexOf(":") + 1));
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        HttpHeaderFilter filter = new HttpHeaderFilter();
        filter.filter(fullRequest, ctx);
        HttpInboundHandler handler = new HttpInboundHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new HttpResponseDecoder())
                                .addLast(new HttpRequestEncoder())
                                .addLast(new HttpObjectAggregator(1024 * 1024))
                                .addLast(handler);
                    }
                });
        try {
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().writeAndFlush(fullRequest).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        handleResponse(fullRequest, ctx, handler.getMessage());
    }

    private void handleResponse(final FullHttpRequest fullRequest,
                                final ChannelHandlerContext ctx,
                                final String value) {
        if (value == null) {
            return;
        }
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", value.getBytes().length);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    assert response != null;
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            ctx.close();
        }
    }
}