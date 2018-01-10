package netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 *     处理由行位分隔符的帧
 * 开发人员: @author dongzc15247<br>
 * 开发时间: 2018-01-10<br>
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //这个解码器，将提取的帧，转发给下一个入站处理器
        //在这里也可以用 DelimiterBasedFrameDecoder 这个解码器
        pipeline.addLast(new LineBasedFrameDecoder(64 * 1024));
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
             // 传入了单个帧的内容
        }
    }
}
